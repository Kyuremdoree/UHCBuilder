package switchuhc.uhc_builder.listener;

import lombok.Getter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

@Getter
public class PlayerListener implements Listener {

    private UHC_Builder main;

    public PlayerListener(UHC_Builder pl){
        this.main = pl;
    }

    @EventHandler
    public void onJoin (PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.BLUE + player.getName() +"vient de se connecter !\n id :" + String.valueOf(player.getEntityId()) );
        main.getPlayerList().add(player);
        Bukkit.getLogger().info("[UHC BUILDER] Player Connected");
        Bukkit.broadcastMessage(player.toString());
        switch (main.getGameStatue()){
            case Waiting:
                Inventory playerInv = player.getInventory();
                playerInv.clear();
                playerInv.setItem(4, main.getMenuItem());
                break;
        }
    }

    @EventHandler
    public void onClick (PlayerInteractEvent event){
        Player player = event.getPlayer();
        switch (main.getGameStatue()){
            case Waiting:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getItem() != null) {
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
        if (main.getGame().getTemps().getTempsActuel() <= 30 && main.getGameStatue().ordinal() < 4){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerDie(PlayerDeathEvent event){
        if (main.getGameStatue().ordinal() < 4){
            Player player = event.getEntity();
            event.setKeepInventory(true);
            event.setKeepLevel(true);
            event.getDrops().clear();
            event.setDroppedExp(0);
            Location loc = player.getLocation();
            player.setGameMode(GameMode.SPECTATOR);

            Bukkit.getScheduler().runTaskTimer(main, new BukkitRunnable() {
                private int timer = 5;

                @Override
                public void run()
                {
                    if(timer == 0){
                        cancel();
                        player.setHealth(player.getMaxHealth());
                        player.teleport(loc);
                        player.setGameMode(GameMode.SURVIVAL);
                        player.setHealth(player.getMaxHealth());

                        Bukkit.getScheduler().runTaskTimer(main, new BukkitRunnable() {
                            private int timer = 5;

                            @Override
                            public void run()
                            {
                                player.setNoDamageTicks(200);
                                if(timer == 0){
                                    player.setNoDamageTicks(0);
                                    event.setKeepInventory(false);
                                    event.setKeepLevel(false);
                                    cancel();
                                }
                                else timer--;
                            }
                        }, 0, 20);
                    }
                    else timer--;
                }
            }, 0, 20);
        }
    }
}
