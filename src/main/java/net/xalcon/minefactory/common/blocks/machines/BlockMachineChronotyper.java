package net.xalcon.minefactory.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineChronotyper;

import javax.annotation.Nullable;

public class BlockMachineChronotyper extends BlockMachineBase implements ITileEntityProvider
{
	public BlockMachineChronotyper()
	{
		super(Material.IRON, "machine_chronotyper");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineChronotyper();
	}
}
