package com.empire.groups.models;

import com.empire.groups.enums.GroupsEnum;

public class Keys {
	
	private String keys, createdBy;
	private GroupsEnum groups;
	private long actived, expire;
	
	public Keys(String createdBy, String keys, GroupsEnum group, long actived, long expire) {
		this.keys = keys;
		this.groups = group;
		this.actived = actived;
		this.expire = expire;
		this.createdBy = createdBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setActived(long actived) {
		this.actived = actived;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setExpire(long expire) {
		this.expire = expire;
	}
	public void setGroups(GroupsEnum groups) {
		this.groups = groups;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public long getActived() {
		return actived;
	}
	public long getExpire() {
		return expire;
	}
	public GroupsEnum getGroups() {
		return groups;
	}
	public String getKeys() {
		return keys;
	}
	
	

}
