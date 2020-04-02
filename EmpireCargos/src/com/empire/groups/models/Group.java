package com.empire.groups.models;

import java.util.UUID;

import com.empire.groups.enums.GroupsEnum;

public class Group {

	private UUID uuid;
	private long actived, expire, firstJoin;
	private int key;
	private GroupsEnum group;
	
	public Group(UUID id, GroupsEnum groups, int key, long actived, long expire, long firstJoin) {
		
		this.uuid = id;
		this.actived = actived;
		this.expire = expire;
		this.firstJoin = firstJoin;
		this.key = 100 + key;
		this.group = groups;
	
	}
	
	public GroupsEnum getGroup() {
		return group;
	}
	public void setGroup(GroupsEnum group) {
		this.group = group;
	}
	public void setKey(int key) {
		this.key = key;
	}
	
	public UUID playerID() {
		return uuid;
	}
	
	public long getActived() {
		return actived;
	}
	
	public long getExpire() {
		return expire;
	}
	
	public long getFirstJoin() {
		return firstJoin;
	}
	
	public void setActived(long actived) {
		this.actived = actived;
	}
	
	public void setExpire(long expire) {
		this.expire = expire;
	}
	
	public void setFirstJoin(long firstJoin) {
		this.firstJoin = firstJoin;
	}
	
	public void setID(UUID uuid) {
		this.uuid = uuid;
	}
	public int getKey() {
		return key;
	}
	
}
