package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;

import javax.annotation.Nullable;

public class BlockMachineBreeder extends BlockMachineBase
{
	public BlockMachineBreeder()
	{
		super("machine_breeder");
	}


	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
	{
		return new TileEntityMachineBreeder();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineBreeder.class;
	}
}
