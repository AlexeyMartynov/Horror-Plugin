package com.bybyzyanka.horror;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

	public ConcurrentMap<String, Integer> glowstoneUsers = new ConcurrentHashMap<>();

	public void onNight() 
	{
		for(Player player : Bukkit.getOnlinePlayers()) 
		{
			if(isGlowstoneUser(player.getName())) continue;

			Location location = player.getLocation();
			if(player.hasPotionEffect(PotionEffectType.GLOWING)
					|| location.distance(HorrorPlugin.getMainWorld().getSpawnLocation()) < 100) continue;

			Set<ProtectedRegion> regions = HorrorPlugin.getWorldGuard().getRegionContainer().get(HorrorPlugin.getMainWorld()).getApplicableRegions(location).getRegions();
			LocalPlayer localPlayer = HorrorPlugin.getWorldGuard().wrapPlayer(player);
			if(regions.stream().anyMatch(region -> region.isMember(localPlayer) || region.isOwner(localPlayer))) continue;
			
			if(!takeGlowstone(player))
			{
				if(player.hasPotionEffect(PotionEffectType.BLINDNESS))
					player.removePotionEffect(PotionEffectType.BLINDNESS);

				player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 5));
			}
		}
	}

	public void glowstoneUse()
	{
		for(String nick : glowstoneUsers.keySet())
		{
			int left = glowstoneUsers.get(nick) - 1;
			if(left <= 0)
			{
				glowstoneUsers.remove(nick);
				break;
			}

			glowstoneUsers.put(nick, left);
		}
	}

	private boolean isGlowstoneUser(String nick)
	{
		return glowstoneUsers.get(nick) == null ? false : true;
	}

	public void removeBlindness(Player player)
	{
		if(player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.hasPotionEffect(PotionEffectType.GLOWING) || isGlowstoneUser(player.getName()))
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
			if(player != taker && player.getLocation().distance(taker.getLocation()) > 10.0D) continue;
			
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
					glowstoneUsers.put(player.getName(), 10);
					return true;
				}
			}
		}
		
		return false;
	}
}