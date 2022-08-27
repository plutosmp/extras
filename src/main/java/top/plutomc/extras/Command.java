package top.plutomc.extras;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class Command implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            ExtrasPlugin.getInstance().reloadConfig();
            Module.reload();
            sender.sendMessage(
                    MiniMessage.miniMessage().deserialize("<green>成功。")
            );
            return true;
        }
        sender.sendMessage(
                MiniMessage.miniMessage().deserialize("<red>请输入参数。")
        );
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("reload");
        }
        return null;
    }
}