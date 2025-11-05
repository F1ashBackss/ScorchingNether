package me.f1ashbackss.scorchingNether;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CommandHandler implements CommandExecutor {

    private final JavaPlugin plugin;
    private TaskManager taskManager;

    public CommandHandler(JavaPlugin plugin, TaskManager taskManager) {
        this.plugin = plugin;
        this.taskManager = taskManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§cИспользование: /sn reload | enable | disable");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                plugin.getConfig().options().copyDefaults(true);
                plugin.saveConfig();

                taskManager.stop();
                taskManager = new TaskManager(plugin);
                taskManager.start();
                sender.sendMessage("§aКонфиг перезагружен.");
            }
            case "enable" -> {
                if (plugin.getConfig().getBoolean("enabled", true))
                    sender.sendMessage("§aScorchingNether уже включён.");
                else {
                    plugin.getConfig().set("enabled", true);
                    plugin.saveConfig();
                    taskManager.start();
                    sender.sendMessage("§aScorchingNether включён.");
                }
            }
            case "disable" -> {
                if (!plugin.getConfig().getBoolean("enabled", true))
                    sender.sendMessage("§aScorchingNether уже отключён.");
                else {
                    plugin.getConfig().set("enabled", false);
                    plugin.saveConfig();
                    taskManager.stop();
                    sender.sendMessage("§eScorchingNether отключён.");
                }
            }
            default -> {
                sender.sendMessage("§cНеизвестная подкоманда: " + args[0]);
            }
        }
        return true;
    }

}
