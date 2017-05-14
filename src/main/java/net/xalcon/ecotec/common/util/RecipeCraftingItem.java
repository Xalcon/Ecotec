package net.xalcon.ecotec.common.util;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.lang.reflect.Type;

public class RecipeCraftingItem
{
	private String item;
	private int count;
	private int meta;
	private String nbt;

	public RecipeCraftingItem() {}

	public RecipeCraftingItem(String item)
	{
		this.item = item;
	}

	public RecipeCraftingItem(String item, int count, int meta, String nbt)
	{
		this.item = item;
		this.nbt = nbt;
		this.count = count;
		this.meta = meta;
	}

	public Object toInputItem()
	{
		if(!this.item.contains(":"))
			return this.item;
		return ForgeRegistries.ITEMS.getValue(new ResourceLocation(this.item));
	}

	public ItemStack toItemStack()
	{
		String[] token = this.item.split("@");
		ResourceLocation loc = new ResourceLocation(token[0]);
		Item item = ForgeRegistries.ITEMS.getValue(loc);
		if(item == null)
			throw new UnsupportedOperationException("Item not found: " + this.item);

		ItemStack itemStack = new ItemStack(item, this.count, this.meta);
		try
		{
			itemStack.setTagCompound(JsonToNBT.getTagFromJson(this.nbt));
		} catch (NBTException e)
		{
			e.printStackTrace();
		}
		return itemStack;
	}

	public static class RecipeCraftingItemJsonParser implements JsonDeserializer<RecipeCraftingItem>
	{
		@Override
		public RecipeCraftingItem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			if(json.isJsonObject())
			{
				JsonObject array = (JsonObject)json;
				return new RecipeCraftingItem(array.get("item").getAsString(), array.get("count").getAsInt(), array.get("meta").getAsInt(), array.get("nbt").getAsString());
			}
			return new RecipeCraftingItem(json.getAsString());
		}
	}
}
