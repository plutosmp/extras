package top.plutomc.extras.modules.slimechunk;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.plutomc.extras.CommandModule;
import top.plutomc.extras.Module;

import java.util.List;

public final class SlimeChunkModule extends Module implements CommandModule {
    @Override
    public List<? extends String> commands() {
        return List.of("slimechunk");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equals("slimechunk")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.getLocation().getChunk().isSlimeChunk()) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(
                            "<green>你所在的区块是史莱姆区块。"
                    ));
                    return true;
                }
                player.sendMessage(MiniMessage.miniMessage().deserialize(
                        "<red>你所在的区块不是史莱姆区块。"
                ));
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

    @Override
    public void enable() {

    }

    @Override
    public void disable() {

    }

    @Override
    public boolean shouldEnable() {
        return getConfig().getBoolean("toggle.slimeChunk");
    }
}
