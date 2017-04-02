package net.xalcon.minefactory.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineGrinder;

import javax.annotation.Nullable;

public class BlockMachineGrinder extends BlockMachineBase implements ITileEntityProvider
{
	public BlockMachineGrinder()
	{
		super("machine_grinder");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineGrinder();
	}
}
