package me.ultimate.AS;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AlwaysSpawn extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info(
                "AlwaysSpawn - Created by Ultimate. BukkitDev Page: http://dev.bukkit.org/profiles/ultimate_n00b/");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = ((Player)sender);
        if (cmd.getName().equalsIgnoreCase("alwaysspawn") && p.hasPermission("alwaysspawn.set")) {
            if(getConfig().getString("Location.x") != null)
                getConfig().set("Location", null);
            getConfig().addDefault("Location.world", p.getLocation().getWorld());
            getConfig().addDefault("Location.x", p.getLocation().getX());
            getConfig().addDefault("Location.y", p.getLocation().getY());
            getConfig().addDefault("Location.z", p.getLocation().getZ());
            getConfig().addDefault("Location.pitch", p.getLocation().getPitch());
            getConfig().addDefault("Location.yaw", p.getLocation().getYaw());
            saveConfig();
            p.sendMessage(t("Set the AlwaysSpawn!"));
            return true;
        }
        return false;
    }
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        tp(event.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player)
            tp(event.getEntity());
    }

    void tp(final Player p) {
        if (getConfig().getString("Location.x") != null) {
            final float yaw = getConfig().getInt("Location.yaw");
            final float pitch = getConfig().getInt("Location.pitch");
            final Location loc = new Location(Bukkit.getWorld(getConfig().getString("Location.world")), getConfig()
                    .getInt("Location.x"), getConfig().getInt("Location.y"), getConfig().getInt("Location.z"), pitch,
                    yaw);
            if (!p.hasPermission("alwaysspawn.bypass"))
                p.teleport(loc);
        }
    }
    String t(String msg){
        return ChatColor.translateAlternateColorCodes('&', "&7" + msg);
    }
}
