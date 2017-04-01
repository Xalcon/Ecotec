package net.xalcon.minefactory.common;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public abstract class CommonProxy
{
	public void init() {}

	public void postInit() {}

	public void registerItemRenderer(Item item, int meta, String registryName, String variantName)
	{
	}

	public void registerFluidBlockRendering(Block block, String name) { }
}
