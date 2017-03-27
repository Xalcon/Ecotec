package net.xalcon.minefactory.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.CommonProxy;

import javax.annotation.Nonnull;

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

	@Override
	public void registerFluidBlockRendering(Block block, String name)
	{
		// use a custom state mapper which will ignore the LEVEL property
		ModelLoader.setCustomStateMapper(block, new StateMapperBase()
		{
			ModelResourceLocation fluidLocation = new ModelResourceLocation(MinefactoryMod.MODID + ":fluids", name);

			@Override
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
			{
				return fluidLocation;
			}
		});
	}
}
