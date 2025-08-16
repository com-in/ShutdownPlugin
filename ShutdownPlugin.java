package com.example.shutdown;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ShutdownPlugin extends JavaPlugin {
    private String adminPassword;

    @Override
    public void onEnable() {
        // 保存默认配置
        saveDefaultConfig();
        // 加载配置
        reloadConfig();
        // 获取密码
        adminPassword = getConfig().getString("password", "default123");
        
        // 注册命令
        getCommand("wstop").setExecutor(this);
        
        getLogger().info("服务器关闭插件已加载，预设密码: " + adminPassword);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("wstop")) {
            if (args.length == 0) {
                sender.sendMessage("§c用法: /wstop <密码>");
                return true;
            }
            
            if (args[0].equals(adminPassword)) {
                sender.sendMessage("§a密码正确，正在关闭服务器...");
                getServer().shutdown();
            } else {
                sender.sendMessage("§c密码错误！");
            }
            return true;
        }
        return false;
    }
}
