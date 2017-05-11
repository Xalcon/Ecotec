package net.xalcon.ecotec.common.blocks.machines;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineSludgeBoiler;

import javax.annotation.Nullable;

public class BlockMachineSludgeBoiler extends BlockMachineBase
{
	public BlockMachineSludgeBoiler()
	{
		super("machine_sludge_boiler");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineSludgeBoiler.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineSludgeBoiler();
	}
}
