package me.f1ashbackss.scorchingNether;

import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {
    private final JavaPlugin plugin;

    public EventListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        GameMode gamemode = player.getGameMode();
        boolean inNether = player.getWorld().getEnvironment() == World.Environment.NETHER;
        boolean isSurvival = gamemode == GameMode.SURVIVAL || gamemode == GameMode.ADVENTURE;
        boolean isEnabled = plugin.getConfig().getBoolean("enabled", true);
        player.setVisualFire(inNether && isSurvival && isEnabled);
    }
}
