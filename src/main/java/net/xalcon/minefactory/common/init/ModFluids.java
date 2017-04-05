package net.xalcon.minefactory.common.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.blocks.fluids.BlockMFFluid;
import net.xalcon.minefactory.common.creativetabs.CreativeTabMinefactoryMachines;
import net.xalcon.minefactory.common.fluids.FluidMFBase;

public class ModFluids
{
	public static FluidMFBase FluidMilk;
	public static FluidMFBase FluidMushroomSoup;
	public static FluidMFBase FluidExperienceEssence;
	public static FluidMFBase FluidSewage;
	public static FluidMFBase FluidSludge;

	public static BlockMFFluid BlockFluidMilk;
	public static BlockMFFluid BlockFluidMushroomSoup;
	public static BlockMFFluid BlockFluidExperienceEssence;
	public static BlockMFFluid BlockFluidSewage;
	public static BlockMFFluid BlockFluidSludge;

	public static void init()
	{
		FluidMilk = register(new FluidMFBase("milk"));
		FluidMushroomSoup = register(new FluidMFBase("mushroom_soup"));
		FluidExperienceEssence = register(new FluidMFBase("experience_essence"));
		FluidSewage = register(new FluidMFBase("sewage"));
		FluidSludge = register(new FluidMFBase("sludge"));

		BlockFluidMilk = register(new BlockMFFluid(FluidMilk));
		BlockFluidMushroomSoup = register(new BlockMFFluid(FluidMushroomSoup));
		BlockFluidExperienceEssence = register(new BlockMFFluid(FluidExperienceEssence));
		BlockFluidSewage = register(new BlockMFFluid(FluidSewage));
		BlockFluidSludge = register(new BlockMFFluid(FluidSludge));
	}

	private static <T extends FluidMFBase> T register(T fluid)
	{
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		return fluid;
	}

	private static <T extends BlockMFFluid> T register(T fluidBlock)
	{
		Fluid fluid = fluidBlock.getFluid();
		GameRegistry.register(fluidBlock, new ResourceLocation(MinefactoryMod.MODID, fluid.getName()));
		MinefactoryMod.Proxy.registerFluidBlockRendering(fluidBlock, fluid.getName());
		fluid.setBlock(fluidBlock);
		fluidBlock.setCreativeTab(CreativeTabMinefactoryMachines.Instance);
		return fluidBlock;
	}
}
