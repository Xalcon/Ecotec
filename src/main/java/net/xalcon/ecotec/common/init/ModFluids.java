package net.xalcon.ecotec.common.init;

import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.CommonProxy;
import net.xalcon.ecotec.common.blocks.BlockEcotecFluid;
import net.xalcon.ecotec.common.fluids.FluidMFBase;

public class ModFluids
{
	public static FluidMFBase FluidMilk;
	public static FluidMFBase FluidMushroomSoup;
	public static FluidMFBase FluidMobEssence;
	public static FluidMFBase FluidSewage;
	public static FluidMFBase FluidSludge;

	public static BlockEcotecFluid BlockFluidMilk;
	public static BlockEcotecFluid BlockFluidMushroomSoup;
	public static BlockEcotecFluid BlockFluidMobEssence;
	public static BlockEcotecFluid BlockFluidSewage;
	public static BlockEcotecFluid BlockFluidSludge;

	public static void init()
	{
		CommonProxy proxy = Ecotec.Proxy;
		FluidMilk = proxy.register(new FluidMFBase("milk"));
		FluidMushroomSoup = proxy.register(new FluidMFBase("mushroom_soup"));
		FluidMobEssence = proxy.register(new FluidMFBase("mob_essence"));
		FluidSewage = proxy.register(new FluidMFBase("sewage"));
		FluidSludge = proxy.register(new FluidMFBase("sludge"));

		BlockFluidMilk = proxy.register(new BlockEcotecFluid(FluidMilk));
		BlockFluidMushroomSoup = proxy.register(new BlockEcotecFluid(FluidMushroomSoup));
		BlockFluidMobEssence = proxy.register(new BlockEcotecFluid(FluidMobEssence));
		BlockFluidSewage = proxy.register(new BlockEcotecFluid(FluidSewage));
		BlockFluidSludge = proxy.register(new BlockEcotecFluid(FluidSludge));
	}
}
