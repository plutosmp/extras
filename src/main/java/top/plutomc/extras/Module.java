package top.plutomc.extras;

import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import top.plutomc.extras.modules.autounlockrecipe.AutoUnlockRecipeModule;
import top.plutomc.extras.modules.customrecipe.CustomRecipeModule;
import top.plutomc.extras.modules.slimechunk.SlimeChunkModule;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class Module {

    private final static Set<Module> MODULES = new LinkedHashSet<>();

    // register all the modules here
    public static void registerModules() {
        register(new CustomRecipeModule());
        register(new AutoUnlockRecipeModule());
        register(new SlimeChunkModule());
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
            disable(module);
        }
        MODULES.clear();
    }

    public static void reload() {
        unload();
        load();
    }

    public static void register(Module module) {
        MODULES.add(module);
    }

    public static void disable(Module module) {
        if (module instanceof Listener) {
            HandlerList.unregisterAll((Listener) module);
        }
        if (module instanceof CommandModule) {
            for (String command : ((CommandModule) module).commands()) {
                if (ExtrasPlugin.getInstance().getCommand(command) != null) {
                    ExtrasPlugin.getInstance().getCommand(command).setExecutor(null);
                    ExtrasPlugin.getInstance().getCommand(command).setTabCompleter(null);
                }
            }
        }
        module.disable();
    }

    private static void registerCommand(String commandName, TabExecutor command) {
        if (ExtrasPlugin.getInstance().getCommand(commandName) != null) {
            ExtrasPlugin.getInstance().getCommand(commandName).setExecutor(command);
            ExtrasPlugin.getInstance().getCommand(commandName).setTabCompleter(command);
        }
    }

    public void reloadConfig() {
        ExtrasPlugin.getInstance().reloadConfig();
    }

    public FileConfiguration getConfig() {
        return ExtrasPlugin.getInstance().getConfig();
    }

    public abstract void enable();

    public abstract void disable();

    public abstract boolean shouldEnable();

    public abstract String name();
}