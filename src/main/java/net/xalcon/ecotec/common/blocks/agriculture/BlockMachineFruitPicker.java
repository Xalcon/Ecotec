package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.blocks.IBlockTintable;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineFruitPicker;

import javax.annotation.Nullable;

public class BlockMachineFruitPicker extends BlockMachineBase implements IBlockTintable
{
	public BlockMachineFruitPicker()
	{
		super("machine_fruit_picker");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineFruitPicker.class;
	}


	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineFruitPicker();
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

	@Override
	public int getColorTint(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return 0xFFCC9900;
	}
}
