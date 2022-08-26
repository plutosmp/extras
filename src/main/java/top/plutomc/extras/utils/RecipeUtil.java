package top.plutomc.extras.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import top.plutomc.extras.ExtrasPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class RecipeUtil {
    private RecipeUtil() {
    }

    public static ItemStack getItemStackByOptions(Map<String, Object> options) {
        Material material = Material.valueOf((String) options.get("result.material"));
        int amount = (int) options.get("result.amount");
        String name = (String) options.get("result.name");
        List<String> beforeParsed = ObjectUtil.castList(options.get("result.lore"), String.class);

        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (beforeParsed != null) {
            List<Component> lore = new ArrayList<>();
            beforeParsed.forEach(s -> lore.add(
                    MiniMessage.miniMessage().deserialize(s)
            ));
            itemMeta.lore(lore);
        }

        List<Enchantment> enchantments = new ArrayList<>();

        for (Map.Entry<String, Object> entry : options.entrySet()) {
            if (entry.getKey().startsWith("result.enchantments.")) {
                enchantments.add(
                        new EnchantmentWrapper(entry.getKey().substring(
                                entry.getKey().lastIndexOf(".") + 1
                        )).getEnchantment()
                );
            }
        }

        if (name != null) {
            itemMeta.displayName(
                    MiniMessage.miniMessage().deserialize(name)
            );
        }

        enchantments.forEach(enchantment -> itemMeta.addEnchant(
                enchantment,
                (Integer) options.get("result.enchantments." + enchantment.getKey().getKey().toLowerCase()),
                true
        ));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static NamespacedKey getNamespacedKey(String key) {
        return new NamespacedKey(ExtrasPlugin.getInstance(), key);
    }
}
