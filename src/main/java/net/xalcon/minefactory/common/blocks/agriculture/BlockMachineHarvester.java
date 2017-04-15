package net.xalcon.minefactory.common.blocks.agriculture;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.agriculture.TileEntityMachineHarvester;

import javax.annotation.Nullable;

public class BlockMachineHarvester extends BlockMachineBase
{
	public BlockMachineHarvester()
	{
		super(Material.IRON, "machine_harvester");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
			playerIn.openGui(MinefactoryMod.instance, GuiType.MachineHarvester.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}


	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
	{
		return new TileEntityMachineHarvester();
	}


	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineHarvester.class;
	}
}
