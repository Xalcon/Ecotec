package net.xalcon.minefactory.common.init;

import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.CommonProxy;
import net.xalcon.minefactory.common.blocks.BlockBase;
import net.xalcon.minefactory.common.blocks.agriculture.*;
import net.xalcon.minefactory.common.blocks.logistics.BlockConveyorBelt;
import net.xalcon.minefactory.common.blocks.machines.*;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks
{
	public static Set<BlockBase> BlockList = new HashSet<>();

	public static BlockMachineHarvester MachineHarvester;
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
		return MinefactoryMod.Proxy.register(block);
	}
}
