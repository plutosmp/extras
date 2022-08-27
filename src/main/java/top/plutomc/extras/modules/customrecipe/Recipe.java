package top.plutomc.extras.modules.customrecipe;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import top.plutomc.extras.utils.ObjectUtil;
import top.plutomc.extras.utils.RecipeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recipe extends ShapedRecipe {
    private final Map<String, Object> OPTIONS;

    public Recipe(Map<String, Object> options) {
        super(RecipeUtil.getNamespacedKey((String) options.get("namespace")), RecipeUtil.getItemStackByOptions(options));
        OPTIONS = options;

        List<String> patten = ObjectUtil.castList(options.get("patten"), String.class);
        Map<String, Material> materialMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : options.entrySet()) {
            if (entry.getKey().startsWith("recipe.")) {
                String recipeKey = entry.getKey().substring(entry.getKey().indexOf(".") + 1);
                Material recipeMaterial = Material.getMaterial((String) entry.getValue());
                materialMap.put(recipeKey, recipeMaterial);
            }
        }

        shape(patten.get(0), patten.get(1), patten.get(2));

        for (Map.Entry<String, Material> materialEntry : materialMap.entrySet()) {
            setIngredient(materialEntry.getKey().toCharArray()[0], materialEntry.getValue());
        }
    }
}