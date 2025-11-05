package me.f1ashbackss.scorchingNether;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

public final class TaskManager {

    private final JavaPlugin plugin;
    private BukkitTask visualTask;
    private BukkitTask damageTask;

    public TaskManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        if (!plugin.getConfig().getBoolean("plugin-enabled", true)) return;

        boolean visualFireEnabled = plugin.getConfig().getBoolean("visual.visual-fire-enabled");
        boolean actionBarEnabled = plugin.getConfig().getBoolean("visual.action-bar-enabled");
        boolean damageEnabled = plugin.getConfig().getBoolean("damage.damage-enabled");
        int damageInterval = plugin.getConfig().getInt("damage.damage-interval");
        double damageValue = plugin.getConfig().getDouble("damage.damage-value");
        String actionBarText = plugin.getConfig().getString("visual.action-bar-text");

        if (visualFireEnabled || actionBarEnabled) {
            visualTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    GameMode gamemode = player.getGameMode();
                    boolean inNether = player.getWorld().getEnvironment() == World.Environment.NETHER;
                    boolean isSurvival = gamemode == GameMode.SURVIVAL || gamemode == GameMode.ADVENTURE;

                    if (visualFireEnabled) {
                        player.setVisualFire(inNether && isSurvival);
                    }

                    if (!inNether || !isSurvival || !actionBarEnabled) continue;

                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            new TextComponent(actionBarText));
                }
            }, 0L, 20L);
        }

        if (damageEnabled) {
            damageTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().getEnvironment() != World.Environment.NETHER) continue;
                    if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) continue;
                    if (player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) continue;
                    if (player.getFireTicks() > 0 || player.getLocation().getBlock().getType() == Material.LAVA) continue;

                    player.damage(damageValue, DamageSource.builder(DamageType.ON_FIRE).build());
                }
            }, 0L, damageInterval);
        }
    }

    public void stop() {
        if (visualTask != null && !visualTask.isCancelled()) {
            visualTask.cancel();
        }
        if (damageTask != null && !damageTask.isCancelled()) {
            damageTask.cancel();
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setVisualFire(false);
        }
    }
}
