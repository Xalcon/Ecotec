package net.xalcon.ecotec.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.fluids.BlockMFFluid;
import net.xalcon.ecotec.common.creativetabs.CreativeTabEcotecMachines;
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
		ItemBlock itemBlock = new ItemBlock(block);
		ResourceLocation registryName = block.getRegistryName();
		if (registryName == null)
			throw new NullPointerException("Block registry name must not be null! Blame the developer (Block: " + block.getClass().getName() + "[" + block.getInternalName() + "])");
		itemBlock.setRegistryName(registryName);
		return register(block, itemBlock);
	}

	public <T extends BlockBase> T register(T block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof IAutoRegisterTileEntity)
		{
			IAutoRegisterTileEntity tile = (IAutoRegisterTileEntity) block;
			GameRegistry.registerTileEntity(tile.getTileEntityClass(), tile.getTileEntityRegistryName());
		}

		return block;
	}

	public <T extends FluidMFBase> T register(T fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	public <T extends BlockMFFluid> T register(T fluidBlock)
	{
		Fluid fluid = fluidBlock.getFluid();
		GameRegistry.register(fluidBlock, new ResourceLocation(EcotecMod.MODID, fluid.getName()));
		fluid.setBlock(fluidBlock);
		fluidBlock.setCreativeTab(CreativeTabEcotecMachines.Instance);
		return fluidBlock;
	}

	public <T extends ItemBase> T register(T item)
	{
		item.setCreativeTab(CreativeTabEcotecMachines.Instance);
		GameRegistry.register(item);
		return item;
	}
}
