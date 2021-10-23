package com.bybyzyanka.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import com.bybyzyanka.horror.HorrorHandler;

public class ListenerManager {

	private PluginManager manager = Bukkit.getPluginManager();
	
	public HorrorHandler horrorHandler = new HorrorHandler();

	public void register() 
	{
		manager.registerEvents(horrorHandler, HorrorPlugin.getInstance());
	}
}
