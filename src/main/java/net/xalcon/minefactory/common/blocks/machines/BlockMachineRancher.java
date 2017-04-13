package net.xalcon.minefactory.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.blocks.IBlockTintable;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

import javax.annotation.Nullable;

public class BlockMachineRancher extends BlockMachineBase implements IBlockTintable
{
	public BlockMachineRancher()
	{
		super("machine_rancher");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
			playerIn.openGui(MinefactoryMod.instance, GuiType.MachineRancher.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineRancher();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineRancher.class;
	}

	@Override
	public int getColorTint(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
	{
		return 0xFF00FFFF;
	}
}
