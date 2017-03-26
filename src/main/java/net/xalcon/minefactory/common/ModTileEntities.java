package net.xalcon.minefactory.common;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineAutoDisenchanter;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBreeder;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineChronotyper;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineHarvester;

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
