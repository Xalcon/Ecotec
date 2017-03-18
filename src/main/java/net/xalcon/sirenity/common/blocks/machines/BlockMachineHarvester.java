package net.xalcon.sirenity.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.sirenity.SirenityMod;
import net.xalcon.sirenity.common.GuiType;
import net.xalcon.sirenity.common.blocks.BlockMachineBase;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineHarvester;

import javax.annotation.Nullable;

public class BlockMachineHarvester extends BlockMachineBase implements ITileEntityProvider
{
	public BlockMachineHarvester()
	{
		super(Material.IRON, "machine_harvester");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
			playerIn.openGui(SirenityMod.instance, GuiType.MachineHarvester.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineHarvester();
	}
}
