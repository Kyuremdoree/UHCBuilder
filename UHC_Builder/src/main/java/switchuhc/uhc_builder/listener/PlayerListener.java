package switchuhc.uhc_builder.listener;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerListener implements Listener {

    private UHC_Builder main;

    public PlayerListener(UHC_Builder pl){
        this.main = pl;
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setScoreboard(main.getGame().getScoreboard());
        //main.getGame().getCustomScoreboard().updateScoreBoardKills(player);
        switch (main.getGameStatue()){
            case Teleportation:
            case Starting:
            case Waiting:
                event.setJoinMessage(ChatColor.BLUE + player.getName() +" vient de se connecter !\n");
                Bukkit.getLogger().info("[UHC BUILDER] Player Connected");
                main.getPlayerList().add(player);
                main.getPlayerInGameList().add(player.getUniqueId());
                Inventory playerInv = player.getInventory();
                playerInv.clear();
                if (player.isOp()){
                    playerInv.setItem(4, main.getMenuItem());
                    player.setGameMode(GameMode.CREATIVE);
                }
                else player.setGameMode(GameMode.SURVIVAL);
                player.setFoodLevel(20);
                player.setExp(0);
                main.getGame().getCustomScoreboard().updateScoreBoardNbPlayer();

                break;
            case Mining:
            case Meetup:
            case Lategame:
            case Terminated:
                if (main.getPlayerInGameList().contains(player.getUniqueId())){
                    event.setJoinMessage(ChatColor.BLUE + player.getName() +" vient de se connecter !\n");
                    Bukkit.getLogger().info("[UHC BUILDER] Player Connected");
                    main.getPlayerList().add(player);
                    main.getGame().getCustomScoreboard().updateScoreBoardNbPlayer();
                }
                else event.setJoinMessage(null);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        Player player = event.getPlayer();
        switch (main.getGameStatue()){
            case Teleportation:
            case Starting:
            case Waiting:
                main.getPlayerInGameList().remove(player.getUniqueId());
                main.getPlayerList().remove(player);
                event.setQuitMessage(ChatColor.BLUE + player.getName() +" vient de se déconnecter !\n");
                Bukkit.getLogger().info("[UHC BUILDER] Player disconnected");
                main.getGame().getCustomScoreboard().updateScoreBoardNbPlayer();
                break;
            case Mining:
            case Meetup:
            case Lategame:
            case Terminated:
                if (main.getPlayerInGameList().contains(player.getUniqueId())){
                    main.getPlayerList().remove(player);
                    event.setQuitMessage(ChatColor.BLUE + player.getName() +" vient de se déconnecter !\n");
                    Bukkit.getLogger().info("[UHC BUILDER] Player disconnected");
                    main.getGame().getCustomScoreboard().updateScoreBoardNbPlayer();
                }
                else event.setQuitMessage(null);
                break;
        }
    }

    @EventHandler
    public void onClick (PlayerInteractEvent event){
        Player player = event.getPlayer();
        switch (main.getGameStatue()){
            case Waiting:
                if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && event.getItem() != null) {
                    if (event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()) {
                        if(event.getItem().getType() == Material.COMMAND) {
                            event.setCancelled(true);
                            player.openInventory(main.getMenuInventory());
                        }
                    }
                }
        }
    }

    @EventHandler
    public void playerTakeDamage(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Player)) return;
        if (main.getGame().getTemps().getTempsActuel() <= 30 && main.getGameStatue().ordinal() < 4){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerTakeDamageFromPlayer(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (main.getGameStatue().ordinal() < 4)
            event.setCancelled(true);

    }

    @EventHandler
    public void NoFood(FoodLevelChangeEvent event){
        if (main.getGameStatue().ordinal() < 3){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDie(PlayerDeathEvent event){
        Player player = event.getEntity();
        if (main.getGameStatue().ordinal() < 4) {
            Location loc = player.getLocation();

            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);

            Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                @Override
                public void run() {
                    player.spigot().respawn();
                    player.setGameMode(GameMode.SPECTATOR);
                    Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(loc);
                            player.setHealth(player.getMaxHealth());
                            player.setGameMode(GameMode.SURVIVAL);
                            player.setNoDamageTicks(200);
                            player.sendMessage(ChatColor.GREEN + "Vous avez été ressuscité !");
                        }
                    }, 5 * 20L); // 5 secondes en ticks
                }
            }, 20L);
        }
        else {
            if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
                //main.getGame().getCustomScoreboard().updateScoreBoardKills(event.getEntity().getKiller());
            }
            Location loc = player.getLocation();

            List<ItemStack> drop = new ArrayList<>(event.getDrops());
            int Exp = event.getDroppedExp();

            event.getDrops().clear();
            event.setDroppedExp(0);

            Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                @Override
                public void run() {
                    player.spigot().respawn();
                    player.setGameMode(GameMode.SPECTATOR);
                    Bukkit.getScheduler().runTaskLater(main, new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.teleport(loc);
                            drop.forEach(item -> Bukkit.getWorld("world").dropItem(loc, item));
                            Bukkit.getWorld("world").spawn(loc, ExperienceOrb.class).setExperience(Exp);
                            main.getPlayerList().remove(player);
                            main.getPlayerInGameList().remove(player.getUniqueId());
                            main.getGame().getCustomScoreboard().updateScoreBoardNbPlayer();
                        }
                    }, 5 * 20L);
                }
            }, 20L);
        }
    }

    @EventHandler
    public void PlayerFusion(InventoryClickEvent event){
        if (event.getClickedInventory() == null) return;
        else if (event.getClickedInventory() instanceof AnvilInventory) {
            AnvilInventory anvilInventory = (AnvilInventory) event.getClickedInventory();
            if (event.getSlotType() == InventoryType.SlotType.RESULT && event.getCurrentItem() != null){
                ItemStack result = event.getCurrentItem();
                ItemStack enchant = findItemMetaByMaterial(main.getGame().getEnchantInventory(), result.getType());
                if (enchant == null) return;
                switch (result.getType()){
                    case DIAMOND_BOOTS:
                    case DIAMOND_HELMET:
                    case DIAMOND_CHESTPLATE:
                    case DIAMOND_LEGGINGS:
                    case IRON_BOOTS:
                    case IRON_CHESTPLATE:
                    case IRON_HELMET:
                    case IRON_LEGGINGS:
                        if (result.getEnchantments().isEmpty() || enchant.getEnchantments().isEmpty()) break;
                        else if (result.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) == null || enchant.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) == null) break;
                        else if (result.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) > enchant.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL)){
                            event.getWhoClicked().sendMessage(ChatColor.RED+("Fusion non Autorisé !"));
                            result.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, enchant.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                        }
                        break;
                    case IRON_SWORD:
                    case DIAMOND_SWORD:
                        if (result.getEnchantments().isEmpty() || enchant.getEnchantments().isEmpty()) break;
                        else if (result.getEnchantments().get(Enchantment.DAMAGE_ALL) == null || enchant.getEnchantments().get(Enchantment.DAMAGE_ALL) == null) break;
                        else if (result.getEnchantments().get(Enchantment.DAMAGE_ALL) > enchant.getEnchantments().get(Enchantment.DAMAGE_ALL)){
                            event.getWhoClicked().sendMessage(ChatColor.RED+("Fusion non Autorisé !"));
                            result.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, enchant.getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                        }
                    case GOLD_SWORD:
                    case STONE_SWORD:
                    case WOOD_SWORD:
                        if (result.getEnchantments().isEmpty() || enchant.getEnchantments().isEmpty()) break;
                        else if (result.getEnchantments().get(Enchantment.KNOCKBACK) == null || enchant.getEnchantments().get(Enchantment.KNOCKBACK) == null) break;
                        else if (result.getEnchantments().get(Enchantment.KNOCKBACK) > enchant.getEnchantments().get(Enchantment.KNOCKBACK)){
                            event.getWhoClicked().sendMessage(ChatColor.RED+("Fusion non Autorisé !"));
                            result.addUnsafeEnchantment(Enchantment.KNOCKBACK, enchant.getEnchantmentLevel(Enchantment.KNOCKBACK));
                        }
                        break;
                    case BOW:
                        if (result.getEnchantments().isEmpty() || enchant.getEnchantments().isEmpty()) break;
                        else if (result.getEnchantments().get(Enchantment.ARROW_DAMAGE) == null || enchant.getEnchantments().get(Enchantment.ARROW_DAMAGE) == null) break;
                        else if (result.getEnchantments().get(Enchantment.ARROW_DAMAGE) > enchant.getEnchantments().get(Enchantment.ARROW_DAMAGE)){
                            event.getWhoClicked().sendMessage(ChatColor.RED+("Fusion non Autorisé !"));
                            result.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, enchant.getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                        }
                        break;
                    case BLAZE_POWDER:
                        if (result.getEnchantments().isEmpty() || enchant.getEnchantments().isEmpty()) break;
                        else if (result.getEnchantments().get(Enchantment.FIRE_ASPECT) == null || enchant.getEnchantments().get(Enchantment.KNOCKBACK) == null) break;
                        else if (result.getEnchantments().get(Enchantment.FIRE_ASPECT) > enchant.getEnchantments().get(Enchantment.KNOCKBACK)){
                            event.getWhoClicked().sendMessage(ChatColor.RED+("Fusion non Autorisé !"));
                            result.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, enchant.getEnchantmentLevel(Enchantment.FIRE_ASPECT));
                        }
                        break;
                }
            }
        }
    }

    @EventHandler
    public void PlayerEnchant(EnchantItemEvent event){
        ItemStack result = event.getItem();
        ItemStack enchant = findItemMetaByMaterial(main.getGame().getEnchantInventory(), result.getType());
        if (enchant == null) return;
        switch (result.getType()){
            case DIAMOND_BOOTS:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case IRON_BOOTS:
            case IRON_CHESTPLATE:
            case IRON_HELMET:
            case IRON_LEGGINGS:
                if (enchant.getEnchantments().isEmpty()) break;
                else if (enchant.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL) == null) break;
                else if (event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL) > enchant.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL)){
                    event.getEnchanter().sendMessage(ChatColor.RED+("Enchantement non Autorisé !"));
                    result.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, enchant.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL));
                }
                break;
            case IRON_SWORD:
            case DIAMOND_SWORD:
                if (enchant.getEnchantments().isEmpty()) break;
                else if (enchant.getEnchantments().get(Enchantment.DAMAGE_ALL) == null) break;
                else if (event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL) > enchant.getEnchantments().get(Enchantment.DAMAGE_ALL)){
                    event.getEnchanter().sendMessage(ChatColor.RED+("Enchantement non Autorisé !"));
                    result.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, enchant.getEnchantmentLevel(Enchantment.DAMAGE_ALL));
                }
            case GOLD_SWORD:
            case STONE_SWORD:
            case WOOD_SWORD:
                if (enchant.getEnchantments().isEmpty()) break;
                else if (enchant.getEnchantments().get(Enchantment.KNOCKBACK) == null) break;
                else if (event.getEnchantsToAdd().get(Enchantment.KNOCKBACK) > enchant.getEnchantments().get(Enchantment.KNOCKBACK)){
                    event.getEnchanter().sendMessage(ChatColor.RED+("Enchantement non Autorisé !"));
                    result.addUnsafeEnchantment(Enchantment.KNOCKBACK, enchant.getEnchantmentLevel(Enchantment.KNOCKBACK));
                }
                break;
            case BOW:
                if (enchant.getEnchantments().isEmpty()) break;
                else if (enchant.getEnchantments().get(Enchantment.ARROW_DAMAGE) == null) break;
                else if (event.getEnchantsToAdd().get(Enchantment.ARROW_DAMAGE) > enchant.getEnchantments().get(Enchantment.ARROW_DAMAGE)){
                    event.getEnchanter().sendMessage(ChatColor.RED+("Enchantement non Autorisé !"));
                    result.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, enchant.getEnchantmentLevel(Enchantment.ARROW_DAMAGE));
                }
                break;
            case BLAZE_POWDER:
                if (enchant.getEnchantments().isEmpty()) break;
                else if (enchant.getEnchantments().get(Enchantment.KNOCKBACK) == null) break;
                else if (event.getEnchantsToAdd().get(Enchantment.FIRE_ASPECT) > enchant.getEnchantments().get(Enchantment.KNOCKBACK)){
                    event.getEnchanter().sendMessage(ChatColor.RED+("Enchantement non Autorisé !"));
                    result.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, enchant.getEnchantmentLevel(Enchantment.FIRE_ASPECT));
                }
                break;
        }
    }


    private ItemStack findItemMetaByMaterial(Inventory inventory, Material material) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == material) {
                return item;
            }
        }
        return null;
    }

    @EventHandler
    public void onExpReceive(PlayerExpChangeEvent event){
        event.setAmount((int)(event.getAmount() * main.getGame().getExpMultiplicator()));
    }
}
