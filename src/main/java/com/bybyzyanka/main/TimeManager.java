package com.bybyzyanka.main;

import org.bukkit.Bukkit;

public class TimeManager {

	private boolean night;
	
	public TimeManager()
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HorrorPlugin.getInstance(), new Runnable()
		{
		    @Override
		    public void run()
		    { 
		        if(HorrorPlugin.getMainWorld().getTime() >= 13000) 
		        {
		        	if(!night) night = true;
		        	
	        		HorrorPlugin.getListenerManager().horrorHandler.onNight();
		        }
		        else if(night) night = false;
		        	
		    }
		}, 0, 40); 
	}
	
	public boolean isNight() 
	{
		return night;
	}
}
