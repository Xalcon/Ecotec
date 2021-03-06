package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.CreativeTabEcotec;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

import javax.annotation.Nullable;

public class BlockDeepStorageUnit extends BlockBase implements IAutoRegisterTileEntity, ITileEntityProvider
{
	public BlockDeepStorageUnit()
	{
		super(Material.GROUND, "deep_storage_unit");
		this.setCreativeTab(CreativeTabEcotec.Instance);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityDeepStorageUnit.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDeepStorageUnit();
	}
}
