package net.xalcon.minefactory.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.client.renderer.block.BlockTintColorHandler;
import net.xalcon.minefactory.client.renderer.item.ItemMachineRangeUpgradeColorHandler;
import net.xalcon.minefactory.client.renderer.item.ItemSafariNetColorHandler;
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.blocks.BlockBase;
import net.xalcon.minefactory.common.blocks.IBlockTintable;
import net.xalcon.minefactory.common.blocks.fluids.BlockMFFluid;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.init.ModItems;
import net.xalcon.minefactory.common.items.ItemBase;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineChronotyper.class, new TileEntityDebugRenderer());
		registerTintHandlers();
	}

	@Override
	public <T extends BlockBase> T register(T block, ItemBlock itemBlock)
	{
		T outBlock = super.register(block, itemBlock);
		block.registerItemModels(itemBlock, this::registerItemRenderer);
		return outBlock;
	}

	@Override
	public <T extends ItemBase> T register(T item)
	{
		T outItem = super.register(item);
		item.registerItemModels(this::registerItemRenderer);
		return outItem;
	}

	@Override
	public <T extends BlockMFFluid> T register(T fluidBlock)
	{
		T outFluidBlock = super.register(fluidBlock);
		ModelLoader.setCustomStateMapper(outFluidBlock, new StateMapperBase()
		{
			ModelResourceLocation fluidLocation = new ModelResourceLocation(MinefactoryMod.MODID + ":fluids", outFluidBlock.getFluid().getName());

			@Override
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return fluidLocation;
			}
		});
		return outFluidBlock;
	}

	private void registerTintHandlers()
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(new ItemSafariNetColorHandler(), ModItems.SafariNetSingle, ModItems.SafariNetMulti);
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(new ItemMachineRangeUpgradeColorHandler(), ModItems.MachineRangeUpgrade);

		for(BlockBase block : ModBlocks.BlockList.stream().filter(b -> b instanceof IBlockTintable).collect(Collectors.toList()))
		{
			FMLClientHandler.instance().getClient().getBlockColors().registerBlockColorHandler(BlockTintColorHandler.Instance, block);
		}
	}

	private void registerItemRenderer(Item item, int meta, ResourceLocation registryName, String variantName)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(registryName, variantName));
	}
}
