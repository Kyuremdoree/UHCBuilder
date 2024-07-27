package switchuhc.uhc_builder.listener;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

public class MenuListener implements Listener {
    @Getter
    private UHC_Builder main;

    public MenuListener(UHC_Builder main) {
        this.main = main;
    }

    @EventHandler
    public void onMenuItemClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory() == event.getWhoClicked().getInventory()){
            if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getType() == Material.COMMAND && event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Menu")){
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().openInventory(main.getMenuInventory());
                return;
            }
        }
        if (main.getGameStatue() == GameStatue.Waiting && event.getClickedInventory().equals(main.getMenuInventory()))
        {
            event.setCancelled(true);
            if (event.getCurrentItem() != null || !event.getCurrentItem().hasItemMeta()) {
                switch (event.getCurrentItem().getType()) {
                    case SLIME_BALL:
                        event.getWhoClicked().closeInventory();
                        getMain().getGame().Start();
                        break;
                    case OBSIDIAN:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getGame().getBorderInventory());
                        break;
                    case GOLD_SWORD:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getGame().getPvpInventory());
                        break;
                    case LEATHER_CHESTPLATE:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getStartInventory());
                        break;
                    case ENCHANTED_BOOK:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getGame().getEnchantInventory());
                        break;
                    case WATCH:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getGame().getCycleInventory());
                    default:
                        break;
                }
                return;
            }
        }
    }
}
