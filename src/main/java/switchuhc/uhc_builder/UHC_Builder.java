package switchuhc.uhc_builder;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
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
import switchuhc.uhc_builder.listener.PlayerListener;
import switchuhc.uhc_builder.utilitaires.GameStatue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class UHC_Builder extends JavaPlugin {

    private List<Player> playerList;
    private ProtocolManager protocolMgr;

    private PacketContainer scoreboardPacketContainer;

    private PluginManager pm;
    private GameStatue gameStatue;
    private ItemStack menuItem;

    private Inventory menuInventory;
    private boolean teaming;


    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }


    public ProtocolManager getProtocolMgr() {
        return protocolMgr;
    }

    public void setProtocolMgr(ProtocolManager protocolMgr) {
        this.protocolMgr = protocolMgr;
    }

    public PacketContainer getScoreboardPacketContainer() {
        return scoreboardPacketContainer;
    }

    public PluginManager getPm() {
        return pm;
    }

    public GameStatue getGameStatue() {
        return gameStatue;
    }

    public void setGameStatue(GameStatue gameStatue) {
        this.gameStatue = gameStatue;
    }

    public ItemStack getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(ItemStack menuItem) {
        this.menuItem = menuItem;
    }

    public Inventory getMenuInventory() {
        return menuInventory;
    }

    public void setMenuInventory(Inventory menuInventory) {
        this.menuInventory = menuInventory;
    }

    public boolean isTeaming() {
        return teaming;
    }

    public void setTeaming(boolean teaming) {
        this.teaming = teaming;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("[UHC BUILDER] Plugin Initialized");
        protocolMgr = ProtocolLibrary.getProtocolManager();
        gameStatue = GameStatue.Waiting;
        scoreboardPacketContainer = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
        PlaceholderAPIPlugin placeholderAPIPlugin = (PlaceholderAPIPlugin) Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);

        playerList = new ArrayList<>();

        createMenu();

        setUpCommand();

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
        menuInventory.setItem(31, itActual);
    }

    private void setUpCommand(){

    }

    public void CreateScoreboard(Player player) throws InvocationTargetException {

    }

    public void updateScoreboard(Player player) {


    }

}
