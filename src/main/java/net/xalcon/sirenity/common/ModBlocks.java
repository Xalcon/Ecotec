package net.xalcon.sirenity.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.xalcon.sirenity.common.blocks.BlockBase;
import net.xalcon.sirenity.common.blocks.BlockConveyorBelt;
import net.xalcon.sirenity.common.blocks.machines.BlockMachineChronotyper;
import net.xalcon.sirenity.common.blocks.machines.BlockMachineHarvester;

public class ModBlocks
{
	public static BlockMachineHarvester MachineHarvester;
	public static BlockMachineChronotyper MachineChronotyper;
	public static BlockConveyorBelt ConveyorBelt;


	public static void init()
	{
		MachineHarvester = register(new BlockMachineHarvester());
		MachineChronotyper = register(new BlockMachineChronotyper());
		ConveyorBelt = register(new BlockConveyorBelt());
	}

	private static <T extends BlockBase> T register(T block)
	{
		ItemBlock itemBlock = new ItemBlock(block);
		ResourceLocation registryName = block.getRegistryName();
		if(registryName == null)
			throw new NullPointerException("Block registry name must not be null! Blame the developer (Block: "+block.getClass().getName()+"["+block.getInternalName()+"])");
		itemBlock.setRegistryName(registryName);
		return register(block, itemBlock);
	}

	private static <T extends BlockBase> T register(T block, ItemBlock itemBlock)
	{
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);
		block.registerItemModels(itemBlock);
		return block;
	}
}
