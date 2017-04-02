package net.xalcon.minefactory.common.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.common.tileentities.machines.*;

public class ModTileEntities
{
	public static void init()
	{
		GameRegistry.registerTileEntity(TileEntityMachineHarvester.class, "machine_harvester");
		GameRegistry.registerTileEntity(TileEntityMachineChronotyper.class, "machine_chronotyper");
		GameRegistry.registerTileEntity(TileEntityMachineBreeder.class, "machine_breeder");
		GameRegistry.registerTileEntity(TileEntityMachineRancher.class, "machine_rancher");
		GameRegistry.registerTileEntity(TileEntityMachineGrinder.class, "machine_grinder");
		GameRegistry.registerTileEntity(TileEntityMachineAutoDisenchanter.class, "machine_auto_disenchanter");

	}
}
