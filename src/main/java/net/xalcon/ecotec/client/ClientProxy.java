package net.xalcon.ecotec.client;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.client.renderer.block.BlockTintColorHandler;
import net.xalcon.ecotec.client.renderer.item.ItemMachineRangeUpgradeColorHandler;
import net.xalcon.ecotec.client.renderer.item.ItemSafariNetColorHandler;
import net.xalcon.ecotec.common.CommonProxy;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.IBlockTintable;
import net.xalcon.ecotec.common.blocks.fluids.BlockMFFluid;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.items.ItemBase;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePlanter.class, new TileEntityDebugRenderer<>());
		//ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachineHarvester.class, new TileEntityDebugRenderer<>());
		this.registerTintHandlers();
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
			ModelResourceLocation fluidLocation = new ModelResourceLocation(Ecotec.MODID + ":fluids", outFluidBlock.getFluid().getName());

			@Override
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state)
			{
				return this.fluidLocation;
			}
		});
		return outFluidBlock;
	}

	private void registerTintHandlers()
	{
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemSafariNetColorHandler.Instance, ModItems.SafariNetSingle, ModItems.SafariNetMulti);
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemMachineRangeUpgradeColorHandler.Instance, ModItems.MachineRangeUpgrade);

		for (Block block : ModBlocks.getBlockList().stream().filter(b -> b instanceof IBlockTintable).collect(Collectors.toList()))
		{
			FMLClientHandler.instance().getClient().getBlockColors().registerBlockColorHandler(BlockTintColorHandler.Instance, block);
		}
	}

	private void registerItemRenderer(Item item, int meta, ResourceLocation registryName, String variantName)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(registryName, variantName));
	}
}
