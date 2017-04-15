package net.xalcon.minefactory.client;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public interface IItemRenderRegister
{
	void registerItemRenderer(Item item, int meta, ResourceLocation registryName, String variantName);
}
