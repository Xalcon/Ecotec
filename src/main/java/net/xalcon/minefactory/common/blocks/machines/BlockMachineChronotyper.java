package net.xalcon.minefactory.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.blocks.IBlockTintable;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineChronotyper;

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

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineChronotyper();
	}

	@Override
	public int getColorTint(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return 0xFFFF00FF; // Magenta
	}
}
