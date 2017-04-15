package net.xalcon.minefactory.common.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.xalcon.minefactory.MinefactoryMod;

public class FluidMFBase extends Fluid
{
	public FluidMFBase(String fluidName)
	{
		super(fluidName,
				new ResourceLocation(MinefactoryMod.MODID, "blocks/fluid/" + fluidName + "_still"),
				new ResourceLocation(MinefactoryMod.MODID, "blocks/fluid/" + fluidName + "_flow"));
	}
}
