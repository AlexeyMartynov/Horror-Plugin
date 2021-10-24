package com.bybyzyanka.main;

import com.bybyzyanka.horror.HorrorHandler;
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

					HorrorHandler handler = HorrorPlugin.getListenerManager().horrorHandler;
					handler.glowstoneUse();
	        		handler.onNight();
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
