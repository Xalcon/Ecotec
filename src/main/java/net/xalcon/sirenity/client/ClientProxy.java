package net.xalcon.sirenity.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.xalcon.sirenity.SirenityMod;
import net.xalcon.sirenity.client.renderer.TileEntityDebugRenderer;
import net.xalcon.sirenity.common.CommonProxy;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineChronotyper;

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
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(SirenityMod.MODID + ":" + registryName, variantName));
	}
}
