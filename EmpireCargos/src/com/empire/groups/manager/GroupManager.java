package com.empire.groups.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.empire.groups.Main;
import com.empire.groups.enums.GroupsEnum;
import com.empire.groups.models.Group;

public class GroupManager {

	private Main instance;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public Connection connection;
	public HashMap<UUID, Group> players = new HashMap<UUID, Group>();
	public int playersInt;

	public GroupManager(Main instance) throws SQLException {

		this.instance = instance;
		this.connection = this.instance.database.connection;


		this.ps = this.connection.prepareStatement("SELECT * FROM `groups`");
		this.rs = this.ps.executeQuery();
		while(this.rs.next()) {
			Group groups = new Group(UUID.fromString(rs.getString("playerID")), GroupsEnum.valueOf(rs.getString("rank")), rs.getInt("key"), rs.getLong("actived"), rs.getLong("expire"), rs.getLong("firstLogin"));
			this.players.put(groups.playerID(), groups);
			playersInt++;
		}

		this.ps.close();
		this.rs.close();

	}

	public void checking(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		Group g = this.players.get(player.getUniqueId());

		if(this.players.containsKey(player.getUniqueId())) {

			if(g.getExpire() != -1 && g.getExpire() <= System.currentTimeMillis() && g.getExpire() > 0) {

				player.sendMessage("");
				player.sendMessage("§8(§c!§8) §eO seu §c"+g.getGroup().getName()+" §aexpirou! §eO seu cargo foi alterado automaticamente para §8(§7Membro§8)§e!"); 
				player.sendMessage("");
				g.setActived(System.currentTimeMillis());
				g.setGroup(GroupsEnum.MEMBER);
				g.setExpire(-1);
				try {
					this.ps = this.connection.prepareStatement("UPDATE `groups` SET `rank`='"+g.getGroup().toString()+"', `actived`='"+g.getActived()+"', `expire`='"+g.getExpire()+"' WHERE `playerID`='"+g.playerID()+"';");
					this.ps.executeUpdate();
					this.ps.close();
				}catch(SQLException e) { e.printStackTrace(); }
				this.players.remove(player.getUniqueId());
				this.players.put(g.playerID(), g);

			}
			
		}else {
			try {

				g = new Group(event.getPlayer().getUniqueId(), GroupsEnum.MEMBER, playersInt, System.currentTimeMillis(), -1, System.currentTimeMillis());
				this.ps = this.connection.prepareStatement("INSERT INTO `groups` (`playerID`, `rank`, `key`, `actived`, `expire`, `firstLogin`) VALUES ('"+g.playerID()+"','"+g.getGroup().toString()+"','"+g.getKey()+"','"+g.getActived()+"','"+g.getExpire()+"','"+g.getFirstJoin()+"');");
				this.ps.executeUpdate();
				this.players.put(g.playerID(), g);
				playersInt++;

			}catch(SQLException e) {e.printStackTrace();}
		}

	}
	public void automaticSave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		Player player = e.getPlayer();
		try {
			Group g = this.players.get(player.getUniqueId());
			this.ps = this.connection.prepareStatement("UPDATE `groups` SET `rank`='"+g.getGroup()+"', `actived`='"+g.getActived()+"', `expire`='"+g.getExpire()+"' WHERE `playerID`='"+g.playerID()+"';");
			this.ps.executeUpdate();
			this.ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}



}
