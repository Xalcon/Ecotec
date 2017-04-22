package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.blocks.IBlockTintable;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineChronotyper;

import javax.annotation.Nullable;

public class BlockMachineChronotyper extends BlockMachineBase implements IBlockTintable
{
	public BlockMachineChronotyper()
	{
		super(Material.IRON, "machine_chronotyper");
	}


	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineChronotyper.class;
	}


	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
	{
		return new TileEntityMachineChronotyper();
	}

	@Override
	public int getColorTint(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return 0xFFFF00FF; // Magenta
	}

	@Override
	public boolean hasGui()
	{
		return false;
	}

	@Override
	public int getGuiId()
	{
		return -1;
	}
}
