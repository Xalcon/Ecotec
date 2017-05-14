package net.xalcon.ecotec.common.util;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecipeCrafting
{
	private String type;
	private RecipeCraftingItem out;
	private Map<Character, RecipeCraftingItem> ingredients;
	private String[] matrix;

	public void registerRecipe()
	{
		List<Object> params = new ArrayList<>();
		Collections.addAll(params, this.matrix);
		for(Map.Entry<Character, RecipeCraftingItem> entry : this.ingredients.entrySet())
		{
			params.add(entry.getKey());
			params.add(entry.getValue().toInputItem());
		}

		if("shaped".equals(this.type))
			GameRegistry.addRecipe(new ShapedOreRecipe(this.out.toItemStack(), params.toArray()));
		else
			GameRegistry.addRecipe(new ShapelessOreRecipe(this.out.toItemStack(), params.toArray()));
	}
}
