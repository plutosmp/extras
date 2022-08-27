package top.plutomc.extras;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ExtrasPlugin extends JavaPlugin {
    private static JavaPlugin instance;

    public static JavaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("extras | By GerryYuu");
        getLogger().info("! FOR PLUTOMC SERVER ONLY !");

        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        if (getCommand("extras") != null) {
            getCommand("extras").setExecutor(new Command());
            getCommand("extras").setTabCompleter(new Command());
        }

        getLogger().info("Loading modules...");
        Module.load();
        getLogger().info("Done.");
    }

    @Override
    public void onDisable() {
        Module.unload();
        getServer().getScheduler().cancelTasks(this);
    }
}