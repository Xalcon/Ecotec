package net.xalcon.sirenity.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.sirenity.common.blocks.BlockMachineBase;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineChronotyper;

import javax.annotation.Nullable;

public class BlockMachineChronotyper extends BlockMachineBase implements ITileEntityProvider
{
	public BlockMachineChronotyper()
	{
		super(Material.IRON, "machine_chronotyper");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineChronotyper();
	}
}
