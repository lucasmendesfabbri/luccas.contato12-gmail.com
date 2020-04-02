package com.empire.groups.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.empire.groups.Main;
import com.empire.groups.enums.GroupsEnum;
import com.empire.groups.models.Keys;

public class KeysManager {

	private Main instance;
	private Connection connection;
	
	public HashMap<String, Keys> keys = new HashMap<String, Keys>();
	private int keysCreated;
	
	public KeysManager(Main instance) {
		this.instance = instance;
		this.connection = this.instance.database.connection;
		
		try {
			
			PreparedStatement s = this.connection.prepareStatement("SELECT * FROM `keys`");
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				this.keys.put(rs.getString("key"), new Keys(rs.getString("id"), rs.getString("key").toUpperCase(), GroupsEnum.valueOf(rs.getString("vip")), rs.getLong("actived"), rs.getLong("expire")));
			    keysCreated++;
			    if(this.keys.get(rs.getString("key")).getExpire() <= System.currentTimeMillis()) {
			    	PreparedStatement x = this.instance.database.connection.prepareStatement("DELETE FROM `keys` WHERE `key`='"+this.keys.get(rs.getString("key")).getKeys().toUpperCase()+"'");
					x.executeUpdate();
			    }
			    
			}
			s.close();
			rs.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public int getKeysCreated() {
		return keysCreated;
	}
	
}
