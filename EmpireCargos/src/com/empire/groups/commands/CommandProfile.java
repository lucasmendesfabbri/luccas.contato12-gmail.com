package com.empire.groups.commands;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


import com.empire.groups.Main;
import com.empire.groups.enums.GroupsEnum;
import com.empire.groups.models.Group;

public class CommandProfile implements CommandExecutor{

	private Main instance;
	public CommandProfile(Main instance) {
		this.instance = instance; this.instance.getCommand("profile").setExecutor(this);
		this.instance = instance; this.instance.getCommand("perfil").setExecutor(this);
	}

	protected enum command{
		PERFIL, PROFILE;
	}
	protected enum subCommands{
		MES, ANO, ETERNO;
	}

	private int length = 0;


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {



		if(cmd.getName().equalsIgnoreCase("perfil")||(cmd.getName().equalsIgnoreCase("profile"))) {

			this.length = args.length;

			switch (this.length) {
			case 0:
				sender.sendMessage("§8<§c!§8> §cUsage: /profile <player>");
				return true;
			case 1:
				try {
					if(Bukkit.getPlayer(args[0]).isOnline()&&this.instance.getGroupManager().players.containsKey(Bukkit.getPlayer(args[0]).getUniqueId())&&Bukkit.getPlayer(args[0])!=null) {

						Group t = this.instance.getGroupManager().players.get(Bukkit.getPlayer(args[0]).getUniqueId());

						String timer = t.getExpire() <= -1?"§8(§bNUNCA§8)":"§8(§7"+new SimpleDateFormat("EEEE, dd/MMMMMMMMM/yyyy hh:mm:ss").format(new Date(t.getExpire()));
						sender.sendMessage("");
						sender.sendMessage("§8(§6INFORMAÇÕES DO '"+Bukkit.getPlayer(args[0]).getName()+"'§8)");
						sender.sendMessage("");
						sender.sendMessage("§8<§e*§8> §fGrupo§6: §a" + t.getGroup().getName());
						sender.sendMessage("§8<§e*§8> §fExpira em§6: " + timer);
						sender.sendMessage("§8<§e*§8> §fPrimeiro login§6: §b" + new SimpleDateFormat("EEEE, dd/MMMMMMMMM/yyyy hh:mm:ss").format(new Date(t.getFirstJoin())));
						sender.sendMessage("§8<§e*§8> §fStatus§6: §8(§bONLINE§8)");
						sender.sendMessage("§8<§e*§8> §fID§6: §8(§b#"+t.getKey()+"§8)");
						sender.sendMessage("");

					}

				}catch (NullPointerException e) {
					sender.sendMessage("§cO §8(§7"+args[0]+"§8)§c está offline.");
				}
				return true;
			case 2:
				if(sender.isOp()) {
					sender.sendMessage("§aUsage: /profile <nome> <group> <MES §8(§e1 MÊS§8) §a/ ANUAL §8(§e 1 ANO§8) §a/ ETERNO §8(§eETERNO§8)§a");
					return true;
				}
				sender.sendMessage("§cUsage: /profile <nome>");
				return true;
			default:
				sender.sendMessage("§cUsage: /profile <nome>");
				break;
			}

			String command = args[2];
			if(sender.isOp()) {
				switch(command){


				case "mes":
					if(args[2].equalsIgnoreCase(command)) {

						Group t = this.instance.getGroupManager().players.get(Bukkit.getPlayer(args[0]).getUniqueId());

						t.setActived(System.currentTimeMillis());
						t.setExpire(TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS)+System.currentTimeMillis());
						t.setGroup(GroupsEnum.valueOf(args[1].toUpperCase()));
						try {
							PreparedStatement ps = this.instance.database.connection.prepareStatement("UPDATE `groups` SET `rank`='"+t.getGroup().toString()+"', `actived`='"+t.getActived()+"', `expire`='"+t.getExpire()+"' WHERE `playerID`='"+t.playerID()+"';");
							ps.executeUpdate();

							sender.sendMessage("§aVocê modificou o perfil do §8(§7"+Bukkit.getPlayer(args[0]).getName()+"§8) §acom sucesso.");
							Bukkit.getPlayer(args[0]).sendMessage("");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo §a"+t.getGroup().getName()+" §bfoi adicionado a sua conta!");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo irá expirar §f" + new SimpleDateFormat("EEEE, dd/MMMMMMMMM/yyyy hh:mm:ss").format(new Date(t.getExpire())));
							Bukkit.getPlayer(args[0]).sendMessage("");
							this.instance.getGroupManager().players.remove(t.playerID());
							this.instance.getGroupManager().players.put(t.playerID(), t);

						} catch (SQLException e) {

							e.printStackTrace();
						}catch (IllegalArgumentException e) {
							sender.sendMessage("§cCargo não encontrado.");
							e.printStackTrace();
						}catch (NullPointerException e) {
							sender.sendMessage("§cO §8(§7"+args[0]+"§8)§c está offline.");
						}


					}else {
						sender.sendMessage("§aUsage: /profile <nome> <group> <MES §8(§e1 MÊS§8) §a/ ANUAL §8(§e 1 ANO§8) §a/ ETERNO §8(§eETERNO§8)§a");
						return true;
					}
					return true;
				case "anual":
					if(args[2].equalsIgnoreCase(command)) {

						Group t = this.instance.getGroupManager().players.get(Bukkit.getPlayer(args[0]).getUniqueId());

						t.setActived(System.currentTimeMillis());
						t.setExpire(TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS)*12+System.currentTimeMillis());
						t.setGroup(GroupsEnum.valueOf(args[1].toUpperCase()));
						try {
							PreparedStatement ps = this.instance.database.connection.prepareStatement("UPDATE `groups` SET `rank`='"+t.getGroup().toString()+"', `actived`='"+t.getActived()+"', `expire`='"+t.getExpire()+"' WHERE `playerID`='"+t.playerID()+"';");
							ps.executeUpdate();

							sender.sendMessage("§aVocê modificou o perfil do §8(§7"+Bukkit.getPlayer(args[0]).getName()+"§8) §acom sucesso.");
							Bukkit.getPlayer(args[0]).sendMessage("");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo §a"+t.getGroup().getName()+" §bfoi adicionado a sua conta!");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo irá expirar §f" + new SimpleDateFormat("EEEE, dd/MMMMMMMMM/yyyy hh:mm:ss").format(new Date(t.getExpire())));
							Bukkit.getPlayer(args[0]).sendMessage("");
							this.instance.getGroupManager().players.remove(t.playerID());
							this.instance.getGroupManager().players.put(t.playerID(), t);

						} catch (SQLException e) {

							e.printStackTrace();
						}catch (IllegalArgumentException e) {
							sender.sendMessage("§cCargo não encontrado.");
							e.printStackTrace();
						}catch (NullPointerException e) {
							sender.sendMessage("§cO §8(§7"+args[0]+"§8)§c está offline.");
						}


					}else {
						sender.sendMessage("§aUsage: /profile <nome> <group> <MES §8(§e1 MÊS§8) §a/ ANUAL §8(§e 1 ANO§8) §a/ ETERNO §8(§eETERNO§8)§a");
						return true;
					}
					return true;

				case "eterno":
					if(args[2].equalsIgnoreCase(command)) {

						Group t = this.instance.getGroupManager().players.get(Bukkit.getPlayer(args[0]).getUniqueId());

						t.setActived(System.currentTimeMillis());
						t.setExpire(-1);
						t.setGroup(GroupsEnum.valueOf(args[1].toUpperCase()));
						try {
							PreparedStatement ps = this.instance.database.connection.prepareStatement("UPDATE `groups` SET `rank`='"+t.getGroup().toString()+"', `actived`='"+t.getActived()+"', `expire`='"+t.getExpire()+"' WHERE `playerID`='"+t.playerID()+"';");
							ps.executeUpdate();

							sender.sendMessage("§aVocê modificou o perfil do §8(§7"+Bukkit.getPlayer(args[0]).getName()+"§8) §acom sucesso.");
							Bukkit.getPlayer(args[0]).sendMessage("");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo §a"+t.getGroup().getName()+" §bfoi adicionado a sua conta!");
							Bukkit.getPlayer(args[0]).sendMessage("§bO cargo irá expirar §fNUNCA");
							Bukkit.getPlayer(args[0]).sendMessage("");
							this.instance.getGroupManager().players.remove(t.playerID());
							this.instance.getGroupManager().players.put(t.playerID(), t);

						} catch (SQLException e) {

							e.printStackTrace();
						}catch (IllegalArgumentException e) {
							sender.sendMessage("§cCargo não encontrado.");
							e.printStackTrace();
						}catch (NullPointerException e) {
							sender.sendMessage("§cO §8(§7"+args[0]+"§8)§c está offline.");
						}


					}else {
						sender.sendMessage("§aUsage: /profile <nome> <group> <MES §8(§e1 MÊS§8) §a/ ANUAL §8(§e 1 ANO§8) §a/ ETERNO §8(§eETERNO§8)§a");
						return true;
					}
					return true;
				default:
					sender.sendMessage("§aUsage: /profile <nome> <group> <MES §8(§e1 MÊS§8) §a/ ANUAL §8(§e 1 ANO§8) §a/ ETERNO §8(§eETERNO§8)§a");

					break;
				}
			}else {
				sender.sendMessage("§cUsage: /profile <nome>");
				return true;
			}

			return true;

		}

		return true;
	}

}
