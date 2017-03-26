package net.xalcon.minefactory.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.CommonProxy;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init()
	{
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChronotyper.class, new TileEntityDebugRenderer());
	}

	@Override
	public void registerItemRenderer(Item item, int meta, String registryName, String variantName)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(MinefactoryMod.MODID + ":" + registryName, variantName));
	}
}
