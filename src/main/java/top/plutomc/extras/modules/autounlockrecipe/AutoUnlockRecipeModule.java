package top.plutomc.extras.modules.autounlockrecipe;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.plutomc.extras.ExtrasPlugin;
import top.plutomc.extras.Module;

public final class AutoUnlockRecipeModule extends Module implements Listener {
    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        ExtrasPlugin.getInstance().getServer().dispatchCommand(ExtrasPlugin.getInstance().getServer().getConsoleSender(), "minecraft:recipe give " + event.getPlayer().getName() + " *");
    }

    @Override
    public boolean shouldEnable() {
        return getConfig().getBoolean("toggle.autoUnlockRecipe");
    }
}
