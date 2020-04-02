package com.empire.groups;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.empire.groups.commands.CommandKeys;
import com.empire.groups.commands.CommandProfile;
import com.empire.groups.database.Database;
import com.empire.groups.listeners.PlayerListener;
import com.empire.groups.manager.GroupManager;
import com.empire.groups.manager.KeysManager;
import com.empire.groups.models.Group;

public class Main extends JavaPlugin{

	public String prefix = "§8(§cGRUPO§8)§7: §c";

	public Database database;
	public GroupManager groupManager;
	public KeysManager keysManager;
	public static Main pl;

	public void onEnable() {
		pl = this;
		this.database = new Database();
		try {
			this.groupManager = new GroupManager(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		this.keysManager = new KeysManager(this);
		new CommandKeys(this);
		new PlayerListener(this);
		new CommandProfile(this);

	}
	public void onDisable() {
		for(Player ps : Bukkit.getOnlinePlayers()) {
			try {
				Group t = this.groupManager.players.get(ps.getUniqueId());
				PreparedStatement p = this.database.connection.prepareStatement("UPDATE `groups` SET `rank`='"+t.getGroup().toString()+"', `actived`='"+t.getActived()+"', `expire`='"+t.getExpire()+"' WHERE `playerID`='"+t.playerID()+"';");
				p.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		this.database.closeConnection();
	}

	public GroupManager getGroupManager() {
		return groupManager;
	}

}
