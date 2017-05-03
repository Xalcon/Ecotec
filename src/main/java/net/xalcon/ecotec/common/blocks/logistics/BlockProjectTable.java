package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityProjectTable;

import javax.annotation.Nullable;

public class BlockProjectTable extends BlockBase implements IAutoRegisterTileEntity
{
	public BlockProjectTable()
	{
		super(Material.GROUND, "project_table");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityProjectTable.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityProjectTable();
	}
}
