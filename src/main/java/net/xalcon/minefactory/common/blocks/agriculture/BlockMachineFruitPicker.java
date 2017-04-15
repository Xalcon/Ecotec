package net.xalcon.minefactory.common.blocks.agriculture;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.agriculture.TileEntityMachineFruitPicker;

public class BlockMachineFruitPicker extends BlockMachineBase
{
	public BlockMachineFruitPicker()
	{
		super("machine_fruit_picker");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineFruitPicker.class;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineFruitPicker();
	}
}
