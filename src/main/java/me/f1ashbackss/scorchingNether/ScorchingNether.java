package me.f1ashbackss.scorchingNether;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScorchingNether extends JavaPlugin {

    private TaskManager taskManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        taskManager = new TaskManager(this);
        taskManager.start();

        PluginCommand sn = getCommand("sn");
        if (sn != null) {
            sn.setExecutor(new CommandHandler(this, taskManager));
            sn.setTabCompleter(new CommandTabCompleter());
        }
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        taskManager.stop();
    }
}
