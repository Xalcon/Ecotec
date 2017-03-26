package net.xalcon.sirenity.common;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineAutoDisenchanter;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineBreeder;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineChronotyper;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineHarvester;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityMachineHarvester.class, "machine_harvester");
		GameRegistry.registerTileEntity(TileEntityMachineChronotyper.class, "machine_chronotyper");
		GameRegistry.registerTileEntity(TileEntityMachineBreeder.class, "machine_breeder");
		GameRegistry.registerTileEntity(TileEntityMachineAutoDisenchanter.class, "machine_auto_disenchanter");
	}
}
