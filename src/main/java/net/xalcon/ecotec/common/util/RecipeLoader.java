package net.xalcon.ecotec.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.xalcon.ecotec.Ecotec;
import org.apache.commons.io.IOUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.List;

public class RecipeLoader
{
	private static Gson gson;

	static
	{
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(RecipeCraftingItem.class, new RecipeCraftingItem.RecipeCraftingItemJsonParser());
		gson = builder.create();
	}

	private class RecipeHeader
	{
		private String type;
		private List<RecipeCrafting> recipes;

		public String getType()
		{
			return this.type;
		}

		public void setType(String type)
		{
			this.type = type;
		}
	}

	public static void registerOreDictNames()
	{
		OreDictionary.registerOre("clayHardened", new ItemStack(Blocks.HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("clayHardened", new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("piston", Blocks.PISTON);
		OreDictionary.registerOre("piston", Blocks.STICKY_PISTON);
	}

	public static void initRecipes()
	{
		loadRecipes("/assets/" + Ecotec.MODID + "/recipes/crafting.json");
	}


	public static void loadRecipes(String path)
	{
		loadRecipes(path, Ecotec.class);
	}

	public static void loadRecipes(String path, Class<?> clazz)
	{
		try
		{
			InputStream stream = clazz.getResourceAsStream(path);
			if(stream == null) return;
			RecipeHeader header = gson.fromJson(IOUtils.toString(stream), RecipeHeader.class);
			stream.close();
			header.recipes.forEach(RecipeCrafting::registerRecipe);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void extractRecipes(Class<?> clazz, String path, File recipeDumpFolder)
	{
		throw new NotImplementedException();
		/*try
		{
			InputStream stream = clazz.getResourceAsStream(path);
			if(stream == null) return;
			File outputFile = new File(recipeDumpFolder, String.valueOf(Paths.get(path).getFileName()));
			if(outputFile.exists()) return;

			outputFile.getParentFile().mkdirs();
			OutputStream writer = new FileOutputStream(outputFile);
			byte[] buffer = new byte[1024 * 8];
			while(stream.available() > 0)
			{
				int len = stream.read(buffer, 0, buffer.length);
				if(len > 0)
					writer.write(buffer, 0, len);
			}
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/
	}
}
