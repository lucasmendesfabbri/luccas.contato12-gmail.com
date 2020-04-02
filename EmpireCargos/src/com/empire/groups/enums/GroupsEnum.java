package com.empire.groups.enums;

public enum GroupsEnum {

	MEMBER("§7Membro"),
	VIP("§aVIP"),
	MVP("§6[MVP]"),
	MVPP("§b[MVP§e+§b]"),
	YOUTUBER("§c[YT]"),
	HELPER("§e[Ajudante]"),
	MODERATOR("§2[Moderador]"),
	ADMINISTRATOR("§c[Admin]"),
	GERENTE("§4[Gerente]"),
	MASTER("§6[Master]");
	
	private String name;
	
	private GroupsEnum(String n) {
		this.name = n;
	}
	
	public String getName() {
		return name;
	}
	
	
}
