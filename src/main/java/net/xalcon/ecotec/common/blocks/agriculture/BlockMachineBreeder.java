package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.EcotecMod;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineBreeder;

import javax.annotation.Nullable;

public class BlockMachineBreeder extends BlockMachineBase
{
	public BlockMachineBreeder()
	{
		super("machine_breeder");
	}


	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
	{
		return new TileEntityMachineBreeder();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineBreeder.class;
	}

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiType.MachineBreeder.getId();
	}
}
