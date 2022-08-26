package top.plutomc.extras;

import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class Module {

    private final static Set<Module> MODULES = new HashSet<>();

    // register all the modules here
    public static void registerModules() {

    }

    public static void load() {
        registerModules();
        for (Module module : MODULES) {
            if (!module.shouldEnable()) {
                return;
            }
            if (module instanceof Listener) {
                ExtrasPlugin.getInstance().getServer().getPluginManager().registerEvents((Listener) module, ExtrasPlugin.getInstance());
            }
            if (module instanceof CommandModule commandModule) {
                commandModule.commands().forEach(s -> registerCommand(s, (CommandModule) module));
            }
            module.enable();
        }
    }

    public static void unload() {
        for (Module module : MODULES) {
            module.disable();
            MODULES.remove(module);
        }
    }

    public static void reload() {
        unload();
        load();
    }

    private static void register(Module module) {
        MODULES.add(module);
    }

    private static void unregister(Module module) {
        module.disable();
        MODULES.remove(module);
    }

    private static void registerCommand(String commandName, TabExecutor command) {
        if (ExtrasPlugin.getInstance().getCommand(commandName) != null) {
            ExtrasPlugin.getInstance().getCommand(commandName).setExecutor(command);
            ExtrasPlugin.getInstance().getCommand(commandName).setTabCompleter(command);
        }
    }

    public FileConfiguration getConfig() {
        return ExtrasPlugin.getInstance().getConfig();
    }

    public void reloadConfig() {
        ExtrasPlugin.getInstance().reloadConfig();
    }

    public File getConfigFile() {
        return new File(ExtrasPlugin.getInstance().getDataFolder(), "config.yml");
    }

    public abstract void enable();

    public abstract void disable();

    public abstract boolean shouldEnable();
}