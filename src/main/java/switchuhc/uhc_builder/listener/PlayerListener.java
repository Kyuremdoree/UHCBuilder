package switchuhc.uhc_builder.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

public class PlayerListener implements Listener {
    private UHC_Builder main;

    public UHC_Builder getMain() {
        return main;
    }


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
    public void onInventoryClick (InventoryClickEvent event){
        Inventory inv = event.getClickedInventory();
        if (inv.getTitle().equals(ChatColor.GOLD + "Menu")){
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()){

            }
        }
    }
}
