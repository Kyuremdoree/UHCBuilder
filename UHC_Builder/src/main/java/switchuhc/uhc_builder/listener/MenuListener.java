package switchuhc.uhc_builder.listener;

import lombok.Getter;
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
        if (main.getGameStatue() == GameStatue.Waiting && event.getClickedInventory().equals(main.getMenuInventory()))
        {
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
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
                    default:
                        break;
                }
                event.setCancelled(true);
            }
        }
    }
}
