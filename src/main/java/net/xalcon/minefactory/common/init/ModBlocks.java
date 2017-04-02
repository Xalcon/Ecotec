package net.xalcon.minefactory.common.init;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.blocks.BlockBase;
import net.xalcon.minefactory.common.blocks.BlockConveyorBelt;
import net.xalcon.minefactory.common.blocks.fluids.BlockMFFluid;
import net.xalcon.minefactory.common.blocks.machines.*;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.fluids.FluidMFBase;

public class ModBlocks
{
	public static BlockMachineHarvester MachineHarvester;
	public static BlockMachineChronotyper MachineChronotyper;
	public static BlockMachineBreeder MachineBreeder;
	public static BlockMachineRancher MachineRancher;
	public static BlockMachineGrinder MachineGrinder;
	public static BlockMachineAutoDisenchanter MachineAutoDisenchanter;
	public static BlockConveyorBelt ConveyorBelt;

	public static BlockMFFluid FluidMilk;
	public static BlockMFFluid FluidMushroomSoup;
	public static BlockMFFluid FluidExperienceEssence;
	public static BlockMFFluid FluidSewage;
	public static BlockMFFluid FluidSludge;

	public static void init()
	{
		MachineHarvester = register(new BlockMachineHarvester());
		MachineChronotyper = register(new BlockMachineChronotyper());
		MachineBreeder = register(new BlockMachineBreeder());
		MachineRancher = register(new BlockMachineRancher());
		MachineGrinder = register(new BlockMachineGrinder());
		MachineAutoDisenchanter = register(new BlockMachineAutoDisenchanter());
		ConveyorBelt = register(new BlockConveyorBelt());

		FluidMilk = register(BlockMFFluid.class, new FluidMFBase("milk"));
		FluidMushroomSoup = register(BlockMFFluid.class, new FluidMFBase("mushroom_soup"));
		FluidExperienceEssence = register(BlockMFFluid.class, new FluidMFBase("experience_essence"));
		FluidSewage = register(BlockMFFluid.class, new FluidMFBase("sewage"));
		FluidSludge = register(BlockMFFluid.class, new FluidMFBase("sludge"));
	}

	private static <T extends BlockMFFluid> T register(Class<T> fluidBlockClass, FluidMFBase fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);

		T fluidBlock;
		try
		{
			fluidBlock = fluidBlockClass.getConstructor(fluid.getClass()).newInstance(fluid);
		}
		catch (Exception ex)
		{
			throw new IllegalArgumentException("unable to instantiate fluidBlock for " + fluid.getName(), ex);
		}

		T block = GameRegistry.register(fluidBlock, new ResourceLocation(MinefactoryMod.MODID, fluid.getName()));
		MinefactoryMod.Proxy.registerFluidBlockRendering(block, fluid.getName());
		fluid.setBlock(fluidBlock);
		fluidBlock.setCreativeTab(CreativeTabMinefactoryMachines.Instance);
		return block;
	}

	private static <T extends BlockBase> T register(T block)
	{
		ItemBlock itemBlock = new ItemBlock(block);
		ResourceLocation registryName = block.getRegistryName();
		if(registryName == null)
			throw new NullPointerException("Block registry name must not be null! Blame the developer (Block: "+block.getClass().getName()+"["+block.getInternalName()+"])");
		itemBlock.setRegistryName(registryName);
		return register(block, itemBlock);
	}

	private static <T extends BlockBase> T register(T block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		block.registerItemModels(itemBlock);
		return block;
	}
}
