package net.xalcon.sirenity.common;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineChronotyper;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineHarvester;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityMachineHarvester.class, "machine_harvester");
		GameRegistry.registerTileEntity(TileEntityMachineChronotyper.class, "machine_chronotyper");
	}
}
