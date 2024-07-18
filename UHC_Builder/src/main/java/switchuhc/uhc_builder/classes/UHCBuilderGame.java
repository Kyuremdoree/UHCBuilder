package switchuhc.uhc_builder.classes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.tasks.StartTask;
import switchuhc.uhc_builder.utilitaires.TimeConverter;
import switchuhc.uhc_builder.utilitaires.Timer;

public class UHCBuilderGame implements Listener {
    @Getter
    private UHC_Builder main;

    @Getter @Setter
    private Inventory borderInventory;

    @Getter @Setter
    private Inventory pvpInventory;

    @Getter @Setter
    private Timer temps;

    @Getter @Setter
    private WorldBorder wb;

    @Getter @Setter
    private int nbJour = 1;

    @Getter
    private int BordureSize = 1000;
    public void setBordureSize(int bordureSize) {
        if(bordureSize >= 150 && bordureSize <= 3000)
            BordureSize = bordureSize;
    }

    public UHCBuilderGame(UHC_Builder main) {
        this.main = main;
        borderInventory = Bukkit.createInventory(null, 9*5, ChatColor.DARK_PURPLE+"Paramètres de la Bordure");
        pvpInventory = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE+"Paramètres du PvP");
        temps = new Timer(90*60, 20*60, 30);
        SetupBorderInventory();
        SetupPvPInventory();
    }

    public void Start(){
        Bukkit.broadcastMessage("§4La partie va COMMENCER !");

        World world = Bukkit.getWorld("world");
        wb = world.getWorldBorder();
        wb.setCenter(0,0);
        wb.setSize(getBordureSize()*2);
        wb.setDamageAmount(2);

        StartTask task = new StartTask(main);
        task.runTaskTimer(main,0,20);
    }

    private void SetupBorderInventory(){
        ItemStack plus10 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack plus5 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack plus1 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack compteur = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins10 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins5 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins1 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

        SkullMeta metaPlus10 = (SkullMeta) plus10.getItemMeta();
        SkullMeta metaPlus5 = (SkullMeta) plus5.getItemMeta();
        SkullMeta metaPlus1 = (SkullMeta) plus1.getItemMeta();
        SkullMeta metaCompteur = (SkullMeta) compteur.getItemMeta();
        SkullMeta metaMoins10 = (SkullMeta) moins10.getItemMeta();
        SkullMeta metaMoins5 = (SkullMeta) moins5.getItemMeta();
        SkullMeta metaMoins1 = (SkullMeta) moins1.getItemMeta();

        metaPlus10.setDisplayName("§2+10m");
        metaPlus5.setDisplayName("§2+5m");
        metaPlus1.setDisplayName("§2+1m");
        String tmpBordure = TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsBordure()));
        metaCompteur.setDisplayName("Border : §b" + tmpBordure);
        metaMoins10.setDisplayName("§4-10m");
        metaMoins5.setDisplayName("§4-5m");
        metaMoins1.setDisplayName("§4-1m");

        plus10.setItemMeta(metaPlus10);
        plus5.setItemMeta(metaPlus5);
        plus1.setItemMeta(metaPlus1);
        compteur.setItemMeta(metaCompteur);
        moins10.setItemMeta(metaMoins10);
        moins5.setItemMeta(metaMoins5);
        moins1.setItemMeta(metaMoins1);

        borderInventory.setItem(10, plus10);
        borderInventory.setItem(11, plus5);
        borderInventory.setItem(12, plus1);
        borderInventory.setItem(13, compteur);
        borderInventory.setItem(14, moins1);
        borderInventory.setItem(15, moins5);
        borderInventory.setItem(16, moins10);

        metaPlus10.setDisplayName("§2+100 blocks");
        metaPlus5.setDisplayName("§2+50 blocks");
        metaPlus1.setDisplayName("§2+10 blocks");
        metaCompteur.setDisplayName("Border : §b" + getBordureSize() + " blocks");
        metaMoins10.setDisplayName("§4-100 blocks");
        metaMoins5.setDisplayName("§4-50 blocks");
        metaMoins1.setDisplayName("§4-10 blocks");

        plus10.setItemMeta(metaPlus10);
        plus5.setItemMeta(metaPlus5);
        plus1.setItemMeta(metaPlus1);
        compteur.setItemMeta(metaCompteur);
        moins10.setItemMeta(metaMoins10);
        moins5.setItemMeta(metaMoins5);
        moins1.setItemMeta(metaMoins1);

        borderInventory.setItem(28, plus10);
        borderInventory.setItem(29, plus5);
        borderInventory.setItem(30, plus1);
        borderInventory.setItem(31, compteur);
        borderInventory.setItem(32, moins1);
        borderInventory.setItem(33, moins5);
        borderInventory.setItem(34, moins10);

        ItemStack retour = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta metaRetour = (SkullMeta) retour.getItemMeta();
        metaRetour.setDisplayName("§eRetour");
        pvpInventory.setItem(0, retour);
    }

    private void SetupPvPInventory(){
        ItemStack plus10 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack plus5 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack plus1 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack compteur = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins10 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins5 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack moins1 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

        SkullMeta metaPlus10 = (SkullMeta) plus10.getItemMeta();
        SkullMeta metaPlus5 = (SkullMeta) plus5.getItemMeta();
        SkullMeta metaPlus1 = (SkullMeta) plus1.getItemMeta();
        SkullMeta metaCompteur = (SkullMeta) compteur.getItemMeta();
        SkullMeta metaMoins10 = (SkullMeta) moins10.getItemMeta();
        SkullMeta metaMoins5 = (SkullMeta) moins5.getItemMeta();
        SkullMeta metaMoins1 = (SkullMeta) moins1.getItemMeta();

        metaPlus10.setDisplayName("§2+5m");
        metaPlus5.setDisplayName("§2+1m");
        metaPlus1.setDisplayName("§2+30s");
        metaCompteur.setDisplayName("PvP : §b" + TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsBordure())));
        metaMoins10.setDisplayName("§4-5m");
        metaMoins5.setDisplayName("§4-1m");
        metaMoins1.setDisplayName("§4-30s");

        plus10.setItemMeta(metaPlus10);
        plus5.setItemMeta(metaPlus5);
        plus1.setItemMeta(metaPlus1);
        compteur.setItemMeta(metaCompteur);
        moins10.setItemMeta(metaMoins10);
        moins5.setItemMeta(metaMoins5);
        moins1.setItemMeta(metaMoins1);

        pvpInventory.setItem(1, plus10);
        pvpInventory.setItem(2, plus5);
        pvpInventory.setItem(3, plus1);
        pvpInventory.setItem(4, compteur);
        pvpInventory.setItem(5, moins1);
        pvpInventory.setItem(6, moins5);
        pvpInventory.setItem(7, moins10);

        ItemStack retour = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta metaRetour = (SkullMeta) retour.getItemMeta();
        metaRetour.setDisplayName("§eRetour");
        pvpInventory.setItem(0, retour);
    }

    @EventHandler
    public void onClickSettingGame(InventoryClickEvent event){
        if(event.getClickedInventory().equals(borderInventory)){
            if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()){
                switch (event.getCurrentItem().getItemMeta().getDisplayName()){
                    case "§2+10m":
                        getTemps().incrementBorder(10*60);
                        break;
                    case "§2+5m":
                        getTemps().incrementBorder(5*60);
                        break;
                    case "§2+1m":
                        getTemps().incrementBorder(60);
                        break;
                    case "§4-10m":
                        getTemps().decrementBorder(10*60);
                        break;
                    case "§4-5m":
                        getTemps().decrementBorder(5*60);
                        break;
                    case "§4-1m":
                        getTemps().decrementBorder(60);
                        break;

                    case "§2+100 blocks":
                        setBordureSize(getBordureSize() + 100);
                        break;
                    case "§2+50 blocks":
                        setBordureSize(getBordureSize() + 50);
                        break;
                    case "§2+10 blocks":
                        setBordureSize(getBordureSize() + 10);
                        break;
                    case "§4-100 blocks":
                        setBordureSize(getBordureSize() - 100);
                        break;
                    case "§4-50 blocks":
                        setBordureSize(getBordureSize() - 50);
                        break;
                    case "§4-10 blocks":
                        setBordureSize(getBordureSize() - 10);
                        break;
                    case "§eRetour":
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getMenuInventory());
                        break;
                }
                String tmpBordure = TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsBordure()));
                borderInventory.getItem(13).getItemMeta().setDisplayName("Border : §b" + tmpBordure);
                borderInventory.getItem(31).getItemMeta().setDisplayName("Border : §b" + getBordureSize() + " blocks");
                event.setCancelled(true);
            }
        }

        if(event.getClickedInventory().equals(pvpInventory)){
            if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                    case "§2+10m":
                        getTemps().incrementBorder(10 * 60);
                        break;
                    case "§2+5m":
                        getTemps().incrementBorder(5 * 60);
                        break;
                    case "§2+1m":
                        getTemps().incrementBorder(60);
                        break;
                    case "§4-10m":
                        getTemps().decrementBorder(10 * 60);
                        break;
                    case "§4-5m":
                        getTemps().decrementBorder(5 * 60);
                        break;
                    case "§4-1m":
                        getTemps().decrementBorder(60);
                        break;
                    case "§eRetour":
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getMenuInventory());
                        break;
                }
                pvpInventory.getItem(4).getItemMeta().setDisplayName("PvP : §b" + TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsBordure())));
                event.setCancelled(true);
            }
        }
    }
}
