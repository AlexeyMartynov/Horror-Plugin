package com.bybyzyanka.horror;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.bybyzyanka.main.HorrorPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class HorrorHandler implements Listener {
	
	public void onNight() 
	{
		for(Player player : Bukkit.getOnlinePlayers()) 
		{ 
			if(player.hasPotionEffect(PotionEffectType.GLOWING)) continue;
			
			Set<ProtectedRegion> regions = HorrorPlugin.getWorldGuard().getRegionContainer().get(HorrorPlugin.getMainWorld()).getApplicableRegions(player.getLocation()).getRegions();
			LocalPlayer localPlayer = HorrorPlugin.getWorldGuard().wrapPlayer(player);
			if(regions.stream().anyMatch(region -> region.isMember(localPlayer) || region.isOwner(localPlayer))) continue;
			
			if(!takeGlowstone(player))
				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 440, 5));
			else player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 440, 5));
		}
	}
	
	public void removeBlindess(Player player) 
	{
		if(player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.hasPotionEffect(PotionEffectType.GLOWING))
			player.removePotionEffect(PotionEffectType.BLINDNESS);
	}
		
	private boolean takeGlowstone(Player taker) 
	{
		List<Player> players = new ArrayList<>();
		players.add(taker);
		for(Player value : Bukkit.getOnlinePlayers()) 
		{
			if(value == taker) continue;
			
			players.add(value);
		}

		
		for(Player player : players) 
		{
			if(player.getLocation().distance(taker.getLocation()) > 10.0D) continue;
			
			ItemStack[] contents = player.getInventory().getContents();
			for(int slot = 0; slot < contents.length; slot++) 
			{
				ItemStack item = contents[slot];
				if(item == null) continue;
				
				if(item.getType() == Material.GLOWSTONE_DUST) 
				{
					if(item.getAmount() == 1)
						contents[slot] = null;
					else item.setAmount(item.getAmount() - 1);
					
					player.getInventory().setContents(contents);
					return true;
				}
			}
		}
		
		return false;
	}
}
