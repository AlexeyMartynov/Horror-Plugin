package com.bybyzyanka.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PotionManager {

	// �������� ������������� �������� �� PotionEffect ���, �� � ���� API ��� EntityPotionEffectEvent (Spigot 1.12.2)
	
	public PotionManager() 
	{
		Bukkit.getScheduler().scheduleSyncRepeatingTask(HorrorPlugin.getInstance(), new Runnable()
		{
		    @Override
		    public void run()
		    {
		    	if(HorrorPlugin.getTimeManager().isNight()) 
		    	{
		    		for(Player player : Bukkit.getOnlinePlayers())
		    			HorrorPlugin.getListenerManager().horrorHandler.removeBlindness(player);
		    	}	
		    }
		}, 0, 20);
	}
}
