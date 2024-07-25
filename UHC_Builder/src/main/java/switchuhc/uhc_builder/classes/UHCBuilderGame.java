package switchuhc.uhc_builder.classes;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.tasks.StartTask;
import switchuhc.uhc_builder.utilitaires.TimeConverter;
import switchuhc.uhc_builder.utilitaires.Timer;

import java.io.Console;
import java.util.Map;

public class UHCBuilderGame implements Listener {
    @Getter
    private UHC_Builder main;

    @Getter @Setter
    private Inventory borderInventory;

    @Getter @Setter
    private Inventory pvpInventory;

    @Getter @Setter
    private Inventory enchantInventory;

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
        enchantInventory = Bukkit.createInventory(null, 9*6,ChatColor.DARK_PURPLE+"Limite d'Enchantement");
        temps = new Timer(90*60, 20*60, 30);
        SetupBorderInventory();
        SetupPvPInventory();
        SetupEnchantInventory();
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
        retour.setItemMeta(metaRetour);
        borderInventory.setItem(0, retour);
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
        metaCompteur.setDisplayName("PvP : §b" + TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsPVP())));
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
        retour.setItemMeta(metaRetour);
        pvpInventory.setItem(0, retour);
    }

    private void SetupEnchantInventory() {
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD,1);

        ItemStack bootIron = new ItemStack(Material.IRON_BOOTS,1);
        ItemStack legginIron = new ItemStack(Material.IRON_LEGGINGS,1);
        ItemStack chestplateIron = new ItemStack(Material.IRON_CHESTPLATE, 1);
        ItemStack helmetIron = new ItemStack(Material.IRON_HELMET,1);

        ItemStack diamandSword = new ItemStack(Material.DIAMOND_SWORD,1);

        ItemStack bootDiamond = new ItemStack(Material.DIAMOND_BOOTS,1);
        ItemStack legginDiamond = new ItemStack(Material.DIAMOND_LEGGINGS,1);
        ItemStack chestplateDiamond = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemStack helmetDiamand = new ItemStack(Material.DIAMOND_HELMET,1);

        ItemStack arc = new ItemStack(Material.BOW,1);

        ItemStack woodSword = new ItemStack(Material.WOOD_SWORD,1);

        ItemStack fire = new ItemStack(Material.BLAZE_POWDER, 1);

        enchantInventory.setItem(11,helmetIron);
        enchantInventory.setItem(20,chestplateIron);
        enchantInventory.setItem(29,legginIron);
        enchantInventory.setItem(38,bootIron);
        enchantInventory.setItem(19,ironSword);

        enchantInventory.setItem(22,arc);
        enchantInventory.setItem(31,woodSword);
        enchantInventory.setItem(40, fire);

        enchantInventory.setItem(15,helmetDiamand);
        enchantInventory.setItem(24,chestplateDiamond);
        enchantInventory.setItem(33,legginDiamond);
        enchantInventory.setItem(42,bootDiamond);
        enchantInventory.setItem(25,diamandSword);

        ItemStack retour = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta metaRetour = (SkullMeta) retour.getItemMeta();
        metaRetour.setDisplayName("§eRetour");
        retour.setItemMeta(metaRetour);
        enchantInventory.setItem(0, retour);
    }

    @EventHandler
    public void onClickSettingGame(InventoryClickEvent event){
        if (event.getClickedInventory() == null) return;
        if(event.getClickedInventory().equals(borderInventory)){
            if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()){
                Bukkit.broadcastMessage("Border entry :\n");
                event.setCancelled(true);
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
                Bukkit.broadcastMessage("new tmp : " + tmpBordure);
                ItemMeta itTmp = borderInventory.getItem(13).getItemMeta();
                itTmp.setDisplayName("Border : §b" + tmpBordure);
                borderInventory.getItem(13).setItemMeta(itTmp);

                itTmp = borderInventory.getItem(31).getItemMeta();
                itTmp.setDisplayName("Border : §b" + getBordureSize() + " blocks");
                borderInventory.getItem(31).setItemMeta(itTmp);
            }
        }

        if(event.getClickedInventory().equals(pvpInventory)){
            if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                event.setCancelled(true);
                switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                    case "§2+10m":
                        getTemps().incrementPVP(10 * 60);
                        break;
                    case "§2+5m":
                        getTemps().incrementPVP(5 * 60);
                        break;
                    case "§2+1m":
                        getTemps().incrementPVP(60);
                        break;
                    case "§4-10m":
                        getTemps().decrementPVP(10 * 60);
                        break;
                    case "§4-5m":
                        getTemps().decrementPVP(5 * 60);
                        break;
                    case "§4-1m":
                        getTemps().decrementPVP(60);
                        break;
                    case "§eRetour":
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getMenuInventory());
                        break;
                }
                ItemMeta itTmp = pvpInventory.getItem(4).getItemMeta();
                itTmp.setDisplayName("PvP : §b" + TimeConverter.ToString(TimeConverter.TimeConverteur(getTemps().getTempsPVP())));
                pvpInventory.getItem(4).setItemMeta(itTmp);
            }
        }
        if(event.getClickedInventory().equals(enchantInventory)){
            if(event.getCurrentItem() != null) {
                event.setCancelled(true);
                Map<Enchantment, Integer> enchantment;
                switch (event.getCurrentItem().getType()){
                    case IRON_SWORD:
                    case DIAMOND_SWORD:
                        enchantment = event.getCurrentItem().getEnchantments();

                        if(enchantment.isEmpty()){
                            event.getCurrentItem().addEnchantment(Enchantment.DAMAGE_ALL, 1);
                        }
                        else if(enchantment.get(Enchantment.DAMAGE_ALL) == 5){
                            event.getCurrentItem().removeEnchantment(Enchantment.DAMAGE_ALL);
                        }
                        else
                            event.getCurrentItem().addUnsafeEnchantment(Enchantment.DAMAGE_ALL, enchantment.get(Enchantment.DAMAGE_ALL) + 1);
                        break;
                    case DIAMOND_BOOTS:
                    case DIAMOND_HELMET:
                    case DIAMOND_CHESTPLATE:
                    case DIAMOND_LEGGINGS:
                    case IRON_BOOTS:
                    case IRON_CHESTPLATE:
                    case IRON_HELMET:
                    case IRON_LEGGINGS:
                        enchantment = event.getCurrentItem().getEnchantments();
                        if(enchantment.isEmpty()){
                            event.getCurrentItem().addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
                        }
                        else if(enchantment.get(Enchantment.PROTECTION_ENVIRONMENTAL) == 4){
                            event.getCurrentItem().removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                        }
                        else event.getCurrentItem().addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, enchantment.get(Enchantment.PROTECTION_ENVIRONMENTAL) + 1);
                        break;
                    case BOW:
                        enchantment = event.getCurrentItem().getEnchantments();
                        if(enchantment.isEmpty()){
                            event.getCurrentItem().addEnchantment(Enchantment.ARROW_DAMAGE, 1);
                        }
                        else if(enchantment.get(Enchantment.ARROW_DAMAGE) == 5){
                            event.getCurrentItem().removeEnchantment(Enchantment.ARROW_DAMAGE);
                        }
                        else event.getCurrentItem().addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, enchantment.get(Enchantment.ARROW_DAMAGE) + 1);
                        break;
                    case WOOD_SWORD:
                        enchantment = event.getCurrentItem().getEnchantments();
                        if(enchantment.isEmpty()){
                            event.getCurrentItem().addEnchantment(Enchantment.KNOCKBACK, 1);
                        }
                        else if(enchantment.get(Enchantment.KNOCKBACK) == 2){
                            event.getCurrentItem().removeEnchantment(Enchantment.KNOCKBACK);
                        }
                        else event.getCurrentItem().addUnsafeEnchantment(Enchantment.KNOCKBACK, enchantment.get(Enchantment.KNOCKBACK) + 1);
                        break;
                    case BLAZE_POWDER:
                        enchantment = event.getCurrentItem().getEnchantments();

                        if(enchantment.isEmpty())
                            event.getCurrentItem().addUnsafeEnchantment(Enchantment.FIRE_ASPECT,1);
                        else event.getCurrentItem().removeEnchantment(Enchantment.FIRE_ASPECT);

                        break;
                    case SKULL_ITEM:
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().openInventory(main.getMenuInventory());
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event){
        event.setCancelled(true);
    }
}
