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
import org.bukkit.event.player.PlayerRespawnEvent;
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
        Player p = ((Player) sender);
        if (cmd.getName().equalsIgnoreCase("alwaysspawn") && p.hasPermission("alwaysspawn.set")) {
            getConfig().set("Location.world", p.getLocation().getWorld().getName());
            getConfig().set("Location.x", p.getLocation().getX());
            getConfig().set("Location.y", p.getLocation().getY());
            getConfig().set("Location.z", p.getLocation().getZ());
            getConfig().set("Location.pitch", p.getLocation().getPitch());
            getConfig().set("Location.yaw", p.getLocation().getYaw());
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

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        if (event.getPlayer() instanceof Player)
            tp(event.getPlayer());
    }

    void tp(final Player p) {
        if (getConfig().getString("Location.x") != null) {
            final Location loc = new Location(Bukkit.getWorld(getConfig().getString("Location.world")), getConfig()
                    .getInt("Location.x"), getConfig().getInt("Location.y"), getConfig().getInt("Location.z"),
                    getConfig().getInt("Location.pitch"), getConfig().getInt("Location.yaw"));
            if (!p.hasPermission("alwaysspawn.bypass")) {
                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                    @Override
                    public void run() {
                        p.teleport(loc);
                    }
                }, 1);
            }
        }
    }

    String t(String msg) {
        return ChatColor.translateAlternateColorCodes('&', "&7" + msg);
    }
}
