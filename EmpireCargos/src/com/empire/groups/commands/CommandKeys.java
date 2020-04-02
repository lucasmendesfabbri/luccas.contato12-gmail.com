package com.empire.groups.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.empire.groups.Main;
import com.empire.groups.enums.GroupsEnum;
import com.empire.groups.models.Group;
import com.empire.groups.models.Keys;

public class CommandKeys implements CommandExecutor{

	private Main instance;

	public CommandKeys(Main instance) {
		this.instance = instance;
		this.instance.getCommand("keys").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		// TODO Auto-generated method stub

		if(cmd.getName().equalsIgnoreCase("keys")) {

			Player ps = (Player)sender; 
			Group p = this.instance.getGroupManager().players.get(ps.getUniqueId());
			if(p.getGroup().equals(GroupsEnum.MASTER)||(p.getGroup().equals(GroupsEnum.GERENTE))||(p.getGroup().equals(GroupsEnum.ADMINISTRATOR))) {

				switch(args.length) {

				case 0:
					ps.sendMessage("");
					ps.sendMessage("§b/keys gerar <VIP> <MENSAL | ANUAL>§f- §7Criar §lKEYS§7 de ativação!");
					ps.sendMessage("§b/keys lista §f- §7Ver todas §lKEYS§7 geradas!");
					ps.sendMessage("§b/keys ativar <KEY> §f- §7Ativar §lKEY§7 de ativação!");
					ps.sendMessage("");
					return true;

				default:
					ps.sendMessage("");
					ps.sendMessage("§b/keys gerar <VIP> <MENSAL | ANUAL>§f- §7Criar §lKEYS§7 de ativação!");
					ps.sendMessage("§b/keys lista §f- §7Ver todas §lKEYS§7 geradas!");
					ps.sendMessage("§b/keys ativar <KEY> §f- §7Ativar §lKEY§7 de ativação!");
					ps.sendMessage("");
					break;
				}
				String key = RandomStringUtils.randomAlphanumeric(5)+"-"+RandomStringUtils.randomAlphanumeric(4);
				switch (args[0]) {
				case "gerar":

					if(args[1].equalsIgnoreCase(GroupsEnum.valueOf(args[1].toUpperCase()).toString())) {

						Keys keys = new Keys(ps.getUniqueId().toString(), key, GroupsEnum.valueOf(args[1].toUpperCase()), System.currentTimeMillis(), TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS)+System.currentTimeMillis());

						try {
							switch (args[2]) {
							case "mensal":
								PreparedStatement s = this.instance.database.connection.prepareStatement("INSERT INTO `keys` (`id`,`key`,`vip`,`expire`, `actived`) VALUES ("
										+ "'"+ keys.getCreatedBy()+"','"+keys.getKeys().toUpperCase()+"','"+keys.getGroups()+"','"+keys.getExpire()+"','"+keys.getActived()+"')");
								s.executeUpdate();
								sender.sendMessage("§aVocê criou uma §lKEY§a de ativação para ativar o VIP §a"+keys.getGroups().getName()+"§a com sucesso.");
								sender.sendMessage("§aMais informaçoes:");
								sender.sendMessage("");
								sender.sendMessage("§8<§e*§8> §fN°§6: §8(§b"+keys.getKeys().toUpperCase()+"§8)");
								sender.sendMessage("§8<§e*§8> §fCargo§6: §f"+keys.getGroups().getName());
								sender.sendMessage("§8<§e*§8> §fData criada§6: §f"+new SimpleDateFormat("EEEE, dd/MMMMMMMM/yyyy hh:mm:ss").format(new Date(keys.getActived())));
								sender.sendMessage("§8<§e*§8> §fExpira§6: §8(§a"+new SimpleDateFormat("EEEE, dd/MMMMMMMM/yyyy hh:mm:ss").format(new Date(keys.getExpire())));
								sender.sendMessage("§8<§e*§8> §fCriada pelo(a)6: §a" + Bukkit.getPlayer(UUID.fromString(keys.getCreatedBy())).getName());
								sender.sendMessage("");
								s.close();
								this.instance.keysManager.keys.put(keys.getKeys(), keys);
								return true;
							case "anual":
								keys = new Keys(ps.getUniqueId().toString(), key, GroupsEnum.valueOf(args[1].toUpperCase()), System.currentTimeMillis(), TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS)*12+System.currentTimeMillis());
								s = this.instance.database.connection.prepareStatement("INSERT INTO `keys` (`id`,`key`,`vip`,`expire`, `actived`) VALUES ("
										+ "'"+ keys.getCreatedBy()+"','"+keys.getKeys().toUpperCase()+"','"+keys.getGroups()+"','"+keys.getExpire()+"','"+keys.getActived()+"')");
								s.executeUpdate();
								this.instance.keysManager.keys.put(keys.getKeys(), keys);
								sender.sendMessage("§aVocê criou uma §lKEY§a de ativação para ativar o VIP §a"+keys.getGroups().getName()+"§a com sucesso.");
								sender.sendMessage("§aMais informaçoes:");
								sender.sendMessage("");
								sender.sendMessage("§8<§e*§8> §fN°§6: §8(§b"+keys.getKeys().toUpperCase()+"§8)");
								sender.sendMessage("§8<§e*§8> §fCargo§6: §f"+keys.getGroups().getName());
								sender.sendMessage("§8<§e*§8> §fData criada§6: §f"+new SimpleDateFormat("EEEE, dd/MMMMMMMM/yyyy hh:mm:ss").format(new Date(keys.getActived())));
								sender.sendMessage("§8<§e*§8> §fExpira§6: §8(§a"+new SimpleDateFormat("EEEE, dd/MMMMMMMM/yyyy hh:mm:ss").format(new Date(keys.getExpire())));
								sender.sendMessage("§8<§e*§8> §fCriada pelo(a)6: §a" + Bukkit.getPlayer(UUID.fromString(keys.getCreatedBy())).getName());
								sender.sendMessage("");
								s.close();
								this.instance.keysManager.keys.put(keys.getKeys(), keys);
		
								return true;

							default:
								ps.sendMessage("");
								ps.sendMessage("§b/keys gerar <VIP> <MENSAL | ANUAL>§f- §7Criar §lKEYS§7 de ativação!");
								ps.sendMessage("§b/keys lista §f- §7Ver todas §lKEYS§7 geradas!");
								ps.sendMessage("§b/keys ativar <KEY> §f- §7Ativar §lKEY§7 de ativação!");
								ps.sendMessage("");
								break;
							}

						} catch (SQLException e) {
							e.printStackTrace();
						}
						this.instance.reloadConfig();
					}
					return true;

				case "lista":

					try {
						
						this.instance.reloadConfig();

						PreparedStatement x = this.instance.database.connection.prepareStatement("SELECT * FROM `keys`");
						ResultSet rs = x.executeQuery();
						sender.sendMessage("");
						sender.sendMessage("§eTOTAL DE §lKEYS§e GERADAS§e:");
						sender.sendMessage("");
						
						while(rs.next()) {
							Keys keys = new Keys(rs.getString("id"), rs.getString("key"), GroupsEnum.valueOf(rs.getString("vip")), rs.getLong("actived"), rs.getLong("expire"));
							if(this.instance.keysManager.keys.containsKey(keys.getKeys())) {
								sender.sendMessage("§bGrupo§e: §f"+keys.getGroups().toString()+" "
										+ "§bKEY§e: §8(§f"+keys.getKeys().toString()+"§8) "
										+ "§bExpira§e: §f"+new SimpleDateFormat("EEEE, dd/MMMMMMMM/yyyy hh:mm:ss").format(new Date(keys.getExpire())));
							}
							this.instance.keysManager.keys.put(rs.getString("key"), new Keys(rs.getString("id"), rs.getString("key"), GroupsEnum.valueOf(rs.getString("vip")), rs.getLong("actived"), rs.getLong("expire")));
						}
					
						return true;

					}catch(Exception e) {
						e.printStackTrace();
					}


					return true;
				case "ativar":

					this.instance.reloadConfig();
					
					try {

						if(!this.instance.keysManager.keys.containsKey(args[1].toUpperCase())) {
							sender.sendMessage("§cNão foi encontrado a KEY!");
							sender.sendMessage("§cObservações: É necessário ser identica ao enviada!");
							sender.sendMessage("§cObservações: Pode ter sido utilizada.");
						}else {
							p.setActived(this.instance.keysManager.keys.get(args[1].toUpperCase()).getActived());
							p.setGroup(this.instance.keysManager.keys.get(args[1].toUpperCase()).getGroups());
							p.setExpire(this.instance.keysManager.keys.get(args[1].toUpperCase()).getExpire());
							PreparedStatement x = this.instance.database.connection.prepareStatement("UPDATE `groups` SET `rank`='"+p.getGroup()+"', `actived`='"+p.getActived()+"', `expire`='"+p.getExpire()+"' WHERE `playerID`='"+p.playerID()+"';");
							x.executeUpdate();
							this.instance.groupManager.players.remove(p.playerID());
							this.instance.groupManager.players.put(p.playerID(), p);
							x.close();
							x = this.instance.database.connection.prepareStatement("DELETE FROM `keys` WHERE `key`='"+this.instance.keysManager.keys.get(args[1].toUpperCase()).getKeys().toUpperCase()+"'");
							x.executeUpdate();
							this.instance.keysManager.keys.remove(args[1].toUpperCase());
							String timer = p.getExpire() <= -1?"§8(§bNUNCA§8)":"§8(§7"+new SimpleDateFormat("EEEE, dd/MMMMMMMMM/yyyy hh:mm:ss").format(new Date(p.getExpire()));
							sender.sendMessage("");
							sender.sendMessage("§aA §lKEY§a foi ativada com sucesso!");
							sender.sendMessage("");
							sender.sendMessage("§8<§e*§8> §fGrupo§6: §a" + p.getGroup().getName());
							sender.sendMessage("§8<§e*§8> §fExpira em§6: " + timer);
							sender.sendMessage("");
							sender.sendMessage("§8<§c!§8> §aRelogue do servidor para ganhar a §lTAG§a!");
							
						}
						return true;

					}catch(Exception e) {
						e.printStackTrace();
					}


					return true;

				default:
					ps.sendMessage("");
					ps.sendMessage("§b/keys gerar <VIP> <MENSAL | ANUAL>§f- §7Criar §lKEYS§7 de ativação!");
					ps.sendMessage("§b/keys lista §f- §7Ver todas §lKEYS§7 geradas!");
					ps.sendMessage("§b/keys ativar <KEY> §f- §7Ativar §lKEY§7 de ativação!");
					ps.sendMessage("");
					break;
				}


			}else {
				sender.sendMessage("§cÉ necessário ser do cargo Admin ou superior!");
			}

			return true;
		}

		return true;
	}



}
