package net.xalcon.ecotec.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.BlockEcotecFluid;
import net.xalcon.ecotec.common.fluids.FluidMFBase;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.init.ModItems;
import net.xalcon.ecotec.common.items.ItemBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		ModBlocks.init();
		ModFluids.init();
		ModItems.init();
	}

	public void init(FMLInitializationEvent event) { }

	@SuppressWarnings("EmptyMethod")
	public void postInit(FMLPostInitializationEvent event) {}

	public <T extends BlockBase> T register(T block)
	{
		ItemBlock itemBlock = block.createItemBlock();
		ResourceLocation registryName = block.getRegistryName();
		if (registryName == null)
			throw new NullPointerException("Block registry name must not be null! Blame the developer (Block: " + block.getClass().getName() + ")");
		itemBlock.setRegistryName(registryName);
		return this.register(block, itemBlock);
	}


	public <T extends BlockBase> T register(T block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof IAutoRegisterTileEntity)
		{
			//noinspection ConstantConditions
			GameRegistry.registerTileEntity(((IAutoRegisterTileEntity) block).getTileEntityClass(), block.getRegistryName().toString());
		}

		return block;
	}

	public <T extends FluidMFBase> T register(T fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	public <T extends BlockEcotecFluid> T register(T fluidBlock)
	{
		Fluid fluid = fluidBlock.getFluid();
		GameRegistry.register(fluidBlock, new ResourceLocation(Ecotec.MODID, fluid.getName()));
		fluid.setBlock(fluidBlock);
		fluidBlock.setCreativeTab(CreativeTabEcotec.Instance);
		return fluidBlock;
	}

	public <T extends ItemBase> T register(T item)
	{
		item.setCreativeTab(CreativeTabEcotec.Instance);
		GameRegistry.register(item);
		return item;
	}
}
