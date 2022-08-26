package top.plutomc.extras;

import org.bukkit.command.TabExecutor;

import java.util.List;

public interface CommandModule extends TabExecutor {
    List<? extends String> commands();
}