package com.example.shutdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ShutdownPlugin extends JavaPlugin {
    private String adminPassword;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        adminPassword = getConfig().getString("password", "default123");
        
        getCommand("wstop").setExecutor(this);
        getLogger().info("服务器关闭插件已加载，预设密码: " + adminPassword);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if (!cmd.getName().equalsIgnoreCase("wstop")) {
                return false;
            }

            if (args.length == 0) {
                sender.sendMessage("§c用法: /wstop <密码>");
                return true;
            }

            if (!sender.hasPermission("shutdownplugin.use")) {
                sender.sendMessage("§c你没有权限使用此命令");
                return true;
            }

            if (args[0].equals(adminPassword)) {
                sender.sendMessage("§a密码验证通过，服务器将在3秒后关闭...");
                getServer().getScheduler().runTaskLater(this, () -> {
                    getServer().shutdown();
                }, 60L); // 3秒延迟(20 ticks/秒)
            } else {
                sender.sendMessage("§c密码错误！");
            }
            return true;
        } catch (Exception e) {
            getLogger().warning("执行命令时发生错误: " + e.getMessage());
            sender.sendMessage("§c命令执行出错，请查看服务器日志");
            return true;
        }
    }
}
