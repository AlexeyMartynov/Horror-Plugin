package com.bybyzyanka.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class HorrorPlugin extends JavaPlugin {

	private static HorrorPlugin instance;
	private static ListenerManager listenerManager;
	private static TimeManager timeManager;
	private static PotionManager potionManager;

	public static HorrorPlugin getInstance() 
	{
		return instance;
	}
	
	public void onEnable() 
	{	
		instance = this; 	
		timeManager = new TimeManager();
		potionManager = new PotionManager();
		listenerManager = new ListenerManager();
		listenerManager.register();
	}
	
	public void onDisable() 
	{
		Bukkit.getLogger().severe("this is a test plugin which was made by bybyzyanka for getting the opportunity to provide commercial services (Gravit Launcher Discord Server)");
	}
	
	public static TimeManager getTimeManager() 
	{
		return timeManager;
	}
	
	public static PotionManager getPotionManager() 
	{
		return potionManager;
	}
	
	public static World getMainWorld() 
	{
		return Bukkit.getWorld("world");
	}
	
	public static ListenerManager getListenerManager() 
	{
		return listenerManager;
	}
	
	public static WorldGuardPlugin getWorldGuard() 
	{
		 Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	    if (!(plugin instanceof WorldGuardPlugin))
	        return null; 
 
	    return (WorldGuardPlugin) plugin;
	}
}
