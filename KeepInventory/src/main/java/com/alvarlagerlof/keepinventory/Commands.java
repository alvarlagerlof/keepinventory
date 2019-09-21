package com.alvarlagerlof.keepinventory;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Set;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;


@CommandAlias("keepinventory|ki")
public class Commands extends BaseCommand {

    JavaPlugin plugin;
    FileConfiguration conf;

    public Commands(JavaPlugin plugin) {
        this.plugin = plugin;
        this.conf = plugin.getConfig();
    }

    @Default
    public void onDefault(Player player) { 
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[KeepInventory]&r Please specify a command"));
    }

    @Subcommand("toggle")
    public void toggle(Player player) {
        Boolean prevSetting = conf.getBoolean("players."+String.valueOf(player.getUniqueId()+".setting"));
        conf.set("players."+String.valueOf(player.getUniqueId())+".setting", !prevSetting);
        conf.set("players."+String.valueOf(player.getUniqueId())+".name", player.getName());
        plugin.saveConfig();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[KeepInventory]&r Changed to: " + (!prevSetting ? "ON" : "OFF")));
    }

    @Subcommand("list")
    public void showList(Player player) {

        if (conf.contains("players")) {
            Set<String> set = conf.getConfigurationSection("players").getKeys(false);
            ArrayList<String> keys = new ArrayList<String>(set);
            
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[KeepInventory]&r List of player settings:"));
             
            for (String key : keys) {
                Boolean setting = conf.getBoolean("players."+key+".setting");
                String name = conf.getString("players."+key+".name");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l"+name+"&r: " + (setting ? "ON" : "OFF")));
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[KeepInventory]&r No player have chosen their settings yet"));
        }
   
    }

}