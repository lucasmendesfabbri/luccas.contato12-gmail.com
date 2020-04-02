package com.empire.groups.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.empire.groups.Main;
import com.empire.groups.enums.GroupsEnum;
import com.empire.groups.models.Group;

public class PlayerListener implements Listener{
	
	private Main instance;
	
	public PlayerListener(Main instance) {
		this.instance = instance;
		Bukkit.getPluginManager().registerEvents(this, this.instance);
	}
	
	@EventHandler
	void playerJoin(PlayerJoinEvent e) {
		this.instance.getGroupManager().checking(e);
		
		Player p = e.getPlayer();
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("");
		p.sendMessage("§cVocê está conectado ao IP: ?");
		p.sendMessage("");
		p.setLevel(-100);
		p.setExp(-1);
		p.setGameMode(GameMode.SURVIVAL);
		p.teleport(Bukkit.getWorld("world").getSpawnLocation());
		
	}
	@EventHandler
	void playerChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		
		if(this.instance.getGroupManager().players.containsKey(e.getPlayer().getUniqueId())) {
			Group g = this.instance.getGroupManager().players.get(e.getPlayer().getUniqueId());
			if(g.getGroup()==GroupsEnum.GERENTE||g.getGroup()==GroupsEnum.MASTER) {
				Bukkit.broadcastMessage("");
				Bukkit.broadcastMessage("" + g.getGroup().getName() + " " + e.getPlayer().getName() + ": §f" + e.getMessage());
				Bukkit.broadcastMessage("");
			}else {
				
				String players = g.getGroup() == GroupsEnum.MEMBER ? "§7"+e.getPlayer().getName()+": §7"+e.getMessage() : 
					"" + g.getGroup().getName() + " " + e.getPlayer().getName()+": §f" + e.getMessage();
				Bukkit.broadcastMessage(players);
			}
			
		}else {
			e.setCancelled(true);
		}
		
		
	}

	@EventHandler
	void playerQuit(PlayerQuitEvent e) {
		this.instance.getGroupManager().automaticSave(e);
	}

	
	
}
