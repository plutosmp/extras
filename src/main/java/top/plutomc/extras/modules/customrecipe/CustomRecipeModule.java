package top.plutomc.extras.modules.customrecipe;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.plutomc.extras.CommandModule;
import top.plutomc.extras.ExtrasPlugin;
import top.plutomc.extras.Module;

import java.util.*;

public final class CustomRecipeModule extends Module implements CommandModule {

    private Set<Recipe> recipes;

    private boolean beingReload = false;

    @Override
    public void enable() {
        if (recipes == null) {
            recipes = new LinkedHashSet<>();
        }
        if (beingReload) {
            reloadConfig();
            beingReload = false;
        }
        loadRecipe();
    }

    @Override
    public void disable() {
        for (Recipe recipe : recipes) {
            Bukkit.removeRecipe(recipe.getKey());
        }
        recipes.clear();
    }

    @Override
    public boolean shouldEnable() {
        return getConfig().getBoolean("toggle.customRecipe");
    }

    @Override
    public String name() {
        return "CustomRecipe";
    }

    private void loadRecipe() {
        ConfigurationSection recipeSection = getConfig().getConfigurationSection("customRecipe");
        if (recipeSection != null) {
            for (String key : recipeSection.getKeys(false)) {
                ConfigurationSection optionsSection = recipeSection.getConfigurationSection(key);
                Map<String, Object> options = new HashMap<>();
                if (optionsSection != null) {
                    optionsSection.getKeys(true).forEach(s -> options.put(s, optionsSection.get(s)));
                    register(new Recipe(options));
                }
            }
        }
    }

    @Override
    public List<? extends String> commands() {
        return List.of("recipe");
    }

    public void register(Recipe recipe) {
        Bukkit.removeRecipe(recipe.getKey());
        ExtrasPlugin.getInstance().getServer().addRecipe(recipe);
        recipes.add(recipe);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equals("recipe")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                beingReload = true;
                disable();
                enable();
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
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("reload");
        }
        return null;
    }
}