package net.xalcon.ecotec.common.blocks.power;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.power.TileEntityGenerator;

import javax.annotation.Nullable;

public class BlockGenerator extends BlockMachineBase implements IAutoRegisterTileEntity
{
	public BlockGenerator()
	{
		super(Material.IRON, "generator");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityGenerator.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityGenerator();
	}
}
