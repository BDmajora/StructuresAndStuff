package bdmajora.stuffmod.registrar;

import bdmajora.stuffmod.ModConfig;
import net.minecraft.core.data.DataLoader;

public class RecipeRegistrar {

	public static void registerRecipes() {
		// Load recipes from the workbench.json file
		DataLoader.loadRecipesFromFile("/assets/stuff/recipes/workbench.json");

	}
}
