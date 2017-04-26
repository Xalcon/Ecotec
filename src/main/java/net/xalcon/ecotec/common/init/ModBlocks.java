package net.xalcon.ecotec.common.init;

import net.minecraft.block.Block;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.agriculture.*;
import net.xalcon.ecotec.common.blocks.logistics.BlockConveyorBelt;
import net.xalcon.ecotec.common.blocks.logistics.BlockDeepStorageUnit;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineAutoDisenchanter;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineAutoSpawner;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineGrinder;
import net.xalcon.ecotec.common.blocks.world.BlockRubberTreeLeaves;
import net.xalcon.ecotec.common.blocks.world.BlockRubberTreeLog;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks
{
	private static final Set<Block> BlockList = new HashSet<>();

	public static BlockRubberTreeLog RubberTreeLog;
	public static BlockRubberTreeLeaves RubberTreeLeaves;

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
	public static BlockDeepStorageUnit DeepStorageUnit;

	public static void init()
	{
		RubberTreeLog = register(new BlockRubberTreeLog());
		RubberTreeLeaves = register(new BlockRubberTreeLeaves());

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
		DeepStorageUnit = register(new BlockDeepStorageUnit());
	}

	private static <T extends Block> T register(T block)
	{
		BlockList.add(block);
		return Ecotec.Proxy.register(block);
	}

	public static Set<Block> getBlockList()
	{
		return BlockList;
	}
}
