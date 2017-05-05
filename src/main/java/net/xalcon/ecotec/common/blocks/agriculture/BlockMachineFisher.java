package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineFisher;

import javax.annotation.Nullable;

public class BlockMachineFisher extends BlockBase implements IAutoRegisterTileEntity
{
	public BlockMachineFisher()
	{
		super(Material.GROUND, "machine_fisher");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineFisher.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineFisher();
	}
}
