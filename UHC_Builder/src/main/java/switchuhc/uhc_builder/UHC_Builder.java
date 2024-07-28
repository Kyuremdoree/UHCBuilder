package switchuhc.uhc_builder;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import switchuhc.uhc_builder.classes.UHCBuilderGame;
import switchuhc.uhc_builder.command.HostCommand;
import switchuhc.uhc_builder.listener.MenuListener;
import switchuhc.uhc_builder.listener.PlayerListener;
import switchuhc.uhc_builder.utilitaires.GameStatue;
import switchuhc.uhc_builder.utilitaires.Timer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class UHC_Builder extends JavaPlugin {
    private UHC_Builder main = this;

    @Getter @Setter
    private List<Player> playerList;

    @Getter @Setter
    private List<UUID> playerInGameList;

    @Getter @Setter
    private ProtocolManager protocolMgr;

    @Getter @Setter
    private PacketContainer scoreboardPacketContainer;

    @Getter @Setter
    private PluginManager pm;

    @Getter @Setter
    private GameStatue gameStatue;

    @Getter @Setter
    private ItemStack menuItem;

    @Getter @Setter
    private Inventory menuInventory;

    @Getter @Setter
    private Inventory startInventory;

    @Getter @Setter
    private UHCBuilderGame game;

    @Getter @Setter
    private String gameTitle = "UHC BUILDER";

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[UHC BUILDER] Plugin Initialized");
        Bukkit.getWorlds().forEach(world -> world.setGameRuleValue("naturalRegeneration", "false"));
        protocolMgr = ProtocolLibrary.getProtocolManager();
        gameStatue = GameStatue.Waiting;
        scoreboardPacketContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(main), main);
        pm.registerEvents(new MenuListener(main), main);

        getCommand("host").setExecutor((new HostCommand(main)));

        playerList = new ArrayList<>();
        playerInGameList = new ArrayList<>();

        game = new UHCBuilderGame(main);
        pm.registerEvents(game, main);

        createMenu();

        setUpCommand();

        startInventory = Bukkit.createInventory(null,4*9);
    }

    @Override
    public void onDisable() {
        Bukkit.broadcastMessage("Plugin Disabled");
    }

    private void createMenu(){
        menuItem = new ItemStack(Material.COMMAND, 1);
        ItemMeta itemMeta = menuItem.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + "Menu");
        itemMeta.addEnchant(Enchantment.LUCK,1,true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        menuItem.setItemMeta(itemMeta);

        menuInventory = Bukkit.createInventory(null,9*5, ChatColor.GOLD + "Menu");
        ItemStack itActual;

        itActual = new ItemStack(Material.SLIME_BALL);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Start");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(40, itActual);

        itActual = new ItemStack(Material.GOLD_SWORD);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "PvP");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(10, itActual);

        itActual = new ItemStack(Material.ENCHANTED_BOOK);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Limite d'Enchant");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(13, itActual);

        itActual = new ItemStack(Material.OBSIDIAN);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Bordure");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(16, itActual);

        itActual = new ItemStack(Material.LEATHER_CHESTPLATE);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Inventaire de Départ");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(33, itActual);

        itActual = new ItemStack(Material.WATCH);
        itemMeta = itActual.getItemMeta();
        itemMeta.setDisplayName(ChatColor.DARK_BLUE + "Cycle Jour/Nuit");
        itActual.setItemMeta(itemMeta);
        menuInventory.setItem(29, itActual);

    }

    private void createStartInv(){
        startInventory = Bukkit.createInventory(null,9*4, ChatColor.GOLD + "Inventaire de départ");
    }

    private void setUpCommand(){

    }

    public void CreateScoreboard(Player player) throws InvocationTargetException {

    }

    public void updateScoreboard(Player player) {


    }

}
