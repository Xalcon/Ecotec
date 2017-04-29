package net.xalcon.ecotec.common.init;

import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.CommonProxy;
import net.xalcon.ecotec.common.blocks.fluids.BlockMFFluid;
import net.xalcon.ecotec.common.fluids.FluidMFBase;

public class ModFluids
{
	public static FluidMFBase FluidMilk;
	public static FluidMFBase FluidMushroomSoup;
	public static FluidMFBase FluidMobEssence;
	public static FluidMFBase FluidSewage;
	public static FluidMFBase FluidSludge;

	public static BlockMFFluid BlockFluidMilk;
	public static BlockMFFluid BlockFluidMushroomSoup;
	public static BlockMFFluid BlockFluidMobEssence;
	public static BlockMFFluid BlockFluidSewage;
	public static BlockMFFluid BlockFluidSludge;

	public static void init()
	{
		CommonProxy proxy = Ecotec.Proxy;
		FluidMilk = proxy.register(new FluidMFBase("milk"));
		FluidMushroomSoup = proxy.register(new FluidMFBase("mushroom_soup"));
		FluidMobEssence = proxy.register(new FluidMFBase("mob_essence"));
		FluidSewage = proxy.register(new FluidMFBase("sewage"));
		FluidSludge = proxy.register(new FluidMFBase("sludge"));

		BlockFluidMilk = proxy.register(new BlockMFFluid(FluidMilk));
		BlockFluidMushroomSoup = proxy.register(new BlockMFFluid(FluidMushroomSoup));
		BlockFluidMobEssence = proxy.register(new BlockMFFluid(FluidMobEssence));
		BlockFluidSewage = proxy.register(new BlockMFFluid(FluidSewage));
		BlockFluidSludge = proxy.register(new BlockMFFluid(FluidSludge));
	}
}
