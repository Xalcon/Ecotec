package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachinePlanter;

import javax.annotation.Nullable;

public class BlockMachinePlanter extends BlockMachineBase
{
	public BlockMachinePlanter()
	{
		super("machine_planter");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachinePlanter.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachinePlanter();
	}
}
