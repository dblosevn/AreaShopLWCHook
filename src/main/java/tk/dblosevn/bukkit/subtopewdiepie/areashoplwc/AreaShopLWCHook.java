package tk.dblosevn.bukkit.subtopewdiepie.areashoplwc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.griefcraft.lwc.LWCPlugin;

import me.wiefferink.areashop.AreaShop;
import tk.dblosevn.bukkit.subtopewdiepie.areashoplwc.listeners.AreaShopListener;

public class AreaShopLWCHook extends JavaPlugin {
	private static final boolean DEBUG = false;
    private static AreaShopLWCHook instance;
    private static LWCPlugin lwcInstance;
    private static AreaShop areaShopInstance;
	@Override
    public void onEnable() {
		instance = this;
		
		lwcInstance = (LWCPlugin) Bukkit.getPluginManager().getPlugin("LWC");
		areaShopInstance = (AreaShop) Bukkit.getPluginManager().getPlugin("AreaShop");
		Bukkit.getPluginManager().registerEvents(new AreaShopListener(), this);
	}
	
	@Override
	public void onDisable() {
		
	}
	public static AreaShopLWCHook get() {
		return instance;
	}
	public static LWCPlugin getLwc() {
		return lwcInstance;
	}
	public static AreaShop getAreaShop() {
		return areaShopInstance;
	}
 
    public static void LogEvent(String action) {
    	if (DEBUG) {
    		System.out.println(action);
    	}
    }
}
