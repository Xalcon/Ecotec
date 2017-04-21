package net.xalcon.ecotec.common.init;

import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.agriculture.*;
import net.xalcon.ecotec.common.blocks.logistics.BlockConveyorBelt;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineAutoDisenchanter;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineAutoSpawner;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineGrinder;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks
{
	public static Set<BlockBase> BlockList = new HashSet<>();

	public static BlockMachineHarvester MachineHarvester;
	public static BlockMachinePlanter MachinePlanter;
	public static BlockMachineChronotyper MachineChronotyper;
	public static BlockMachineBreeder MachineBreeder;
	public static BlockMachineRancher MachineRancher;
	public static BlockMachineGrinder MachineGrinder;
	public static BlockMachineFruitPicker MachineFruitPicker;
	public static BlockMachineAutoDisenchanter MachineAutoDisenchanter;
	public static BlockMachineAutoSpawner MachineAutoSpawner;
	public static BlockConveyorBelt ConveyorBelt;

	public static void init()
	{
		MachineHarvester = register(new BlockMachineHarvester());
		MachinePlanter = register(new BlockMachinePlanter());
		MachineChronotyper = register(new BlockMachineChronotyper());
		MachineBreeder = register(new BlockMachineBreeder());
		MachineRancher = register(new BlockMachineRancher());
		MachineGrinder = register(new BlockMachineGrinder());
		MachineFruitPicker = register(new BlockMachineFruitPicker());
		MachineAutoDisenchanter = register(new BlockMachineAutoDisenchanter());
		MachineAutoSpawner = register(new BlockMachineAutoSpawner());
		ConveyorBelt = register(new BlockConveyorBelt());
	}

	private static <T extends BlockBase> T register(T block)
	{
		BlockList.add(block);
		return EcotecMod.Proxy.register(block);
	}
}
