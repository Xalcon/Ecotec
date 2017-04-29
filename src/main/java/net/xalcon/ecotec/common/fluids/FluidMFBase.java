package net.xalcon.ecotec.common.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.xalcon.ecotec.Ecotec;

public class FluidMFBase extends Fluid
{
	public FluidMFBase(String fluidName)
	{
		super(fluidName,
				new ResourceLocation(Ecotec.MODID, "blocks/fluid/" + fluidName + "_still"),
				new ResourceLocation(Ecotec.MODID, "blocks/fluid/" + fluidName + "_flow"));
		this.setUnlocalizedName(Ecotec.MODID + "." + fluidName);
	}
}
