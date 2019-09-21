package com.alvarlagerlof.keepinventory;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.ChatColor;

import co.aikar.commands.PaperCommandManager;

import com.alvarlagerlof.keepinventory.Commands;


public final class Main extends JavaPlugin implements Listener {

    FileConfiguration conf;

    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);

        conf = this.getConfig();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Commands(this));

    }
  
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = (Player) event.getEntity();
        String uuid = player.getUniqueId().toString();

        if (!conf.contains("players."+uuid)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l[KeepInventory]&r You died for the first time with the KeepInvetory plugin. It allows you to personally set if you want keepinventory to be on or off. To change the setting, type /keepinventory toggle"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7---------------"));
            conf.set("players."+String.valueOf(player.getUniqueId())+".setting", true);
            conf.set("players."+String.valueOf(player.getUniqueId())+".name", player.getName());
            this.saveConfig();
        }

        Boolean setting = conf.getBoolean("players."+uuid+".setting");
        event.setKeepInventory(setting);
        event.setKeepLevel(setting);
        event.setDroppedExp(setting ? 0 : event.getDroppedExp());
        if (setting) event.getDrops().clear();
    }
}