package net.xalcon.ecotec.common.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.agriculture.*;
import net.xalcon.ecotec.common.blocks.logistics.*;
import net.xalcon.ecotec.common.blocks.magic.BlockMachineAutoDisenchanter;
import net.xalcon.ecotec.common.blocks.magic.BlockMachineAutoEnchanter;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineAutoSpawner;
import net.xalcon.ecotec.common.blocks.machines.BlockMachineGrinder;
import net.xalcon.ecotec.common.blocks.energy.BlockGenerator;
import net.xalcon.ecotec.common.blocks.world.BlockRubberTreeLeaves;
import net.xalcon.ecotec.common.blocks.world.BlockRubberTreeLog;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityTankSlave;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityTankValve;

import java.util.HashSet;
import java.util.Set;

public class ModBlocks
{
	private static final Set<Block> BlockList = new HashSet<>();

	// Worldgen
	public static BlockRubberTreeLog RubberTreeLog;
	public static BlockRubberTreeLeaves RubberTreeLeaves;

	// Agriculture
	public static BlockMachineHarvester MachineHarvester;
	public static BlockMachinePlanter MachinePlanter;
	public static BlockMachineFruitPicker MachineFruitPicker;
	public static BlockMachineFisher MachineFisher;

	// Farming
	public static BlockMachineChronotyper MachineChronotyper;
	public static BlockMachineBreeder MachineBreeder;
	public static BlockMachineRancher MachineRancher;
	public static BlockMachineGrinder MachineGrinder;

	// Magic
	public static BlockMachineAutoEnchanter MachineAutoEnchanter;
	public static BlockMachineAutoDisenchanter MachineAutoDisenchanter;
	public static BlockMachineAutoSpawner MachineAutoSpawner;

	// Energy
	public static BlockGenerator Generator;

	// Logistics
	public static BlockConveyorBelt ConveyorBelt;
	public static BlockDeepStorageUnit DeepStorageUnit;
	public static BlockProjectTable ProjectTable;
	public static BlockFluidTank FluidTank;

	public static void init()
	{
		RubberTreeLog = register(new BlockRubberTreeLog());
		RubberTreeLeaves = register(new BlockRubberTreeLeaves());

		MachineHarvester = register(new BlockMachineHarvester());
		MachinePlanter = register(new BlockMachinePlanter());
		MachineFruitPicker = register(new BlockMachineFruitPicker());
		MachineFisher = register(new BlockMachineFisher());

		MachineChronotyper = register(new BlockMachineChronotyper());
		MachineBreeder = register(new BlockMachineBreeder());
		MachineRancher = register(new BlockMachineRancher());
		MachineGrinder = register(new BlockMachineGrinder());

		MachineAutoEnchanter = register(new BlockMachineAutoEnchanter());
		MachineAutoDisenchanter = register(new BlockMachineAutoDisenchanter());
		MachineAutoSpawner = register(new BlockMachineAutoSpawner());

		Generator = register(new BlockGenerator());

		ConveyorBelt = register(new BlockConveyorBelt());
		DeepStorageUnit = register(new BlockDeepStorageUnit());
		ProjectTable = register(new BlockProjectTable());

		FluidTank = register(new BlockFluidTank());
		GameRegistry.registerTileEntity(TileEntityTankValve.class, Ecotec.MODID + ":fluid_tank_valve");
		GameRegistry.registerTileEntity(TileEntityTankSlave.class, Ecotec.MODID + ":fluid_tank_slave");
	}

	private static <T extends BlockBase> T register(T block)
	{
		BlockList.add(block);
		return Ecotec.Proxy.register(block);
	}

	public static Set<Block> getBlockList()
	{
		return BlockList;
	}
}
