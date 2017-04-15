package net.xalcon.minefactory.common.init;

import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.blocks.fluids.BlockMFFluid;
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
		CommonProxy proxy = MinefactoryMod.Proxy;
		FluidMilk = proxy.register(new FluidMFBase("milk"));
		FluidMushroomSoup = proxy.register(new FluidMFBase("mushroom_soup"));
		FluidExperienceEssence = proxy.register(new FluidMFBase("experience_essence"));
		FluidSewage = proxy.register(new FluidMFBase("sewage"));
		FluidSludge = proxy.register(new FluidMFBase("sludge"));

		BlockFluidMilk = proxy.register(new BlockMFFluid(FluidMilk));
		BlockFluidMushroomSoup = proxy.register(new BlockMFFluid(FluidMushroomSoup));
		BlockFluidExperienceEssence = proxy.register(new BlockMFFluid(FluidExperienceEssence));
		BlockFluidSewage = proxy.register(new BlockMFFluid(FluidSewage));
		BlockFluidSludge = proxy.register(new BlockMFFluid(FluidSludge));
	}
}
