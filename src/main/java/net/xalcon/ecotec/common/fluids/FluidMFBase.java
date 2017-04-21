package net.xalcon.ecotec.common.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.xalcon.ecotec.EcotecMod;

public class FluidMFBase extends Fluid
{
	public FluidMFBase(String fluidName)
	{
		super(fluidName,
				new ResourceLocation(EcotecMod.MODID, "blocks/fluid/" + fluidName + "_still"),
				new ResourceLocation(EcotecMod.MODID, "blocks/fluid/" + fluidName + "_flow"));
	}
}
