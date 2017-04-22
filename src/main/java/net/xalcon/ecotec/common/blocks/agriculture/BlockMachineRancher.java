package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.blocks.IBlockTintable;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineRancher;

import javax.annotation.Nullable;

public class BlockMachineRancher extends BlockMachineBase implements IBlockTintable
{
	public BlockMachineRancher()
	{
		super("machine_rancher");
	}

	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
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

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiType.MachineRancher.getId();
	}
}
