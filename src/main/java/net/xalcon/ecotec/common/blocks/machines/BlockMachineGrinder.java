package net.xalcon.ecotec.common.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineGrinder;

public class BlockMachineGrinder extends BlockMachineBase
{
	public BlockMachineGrinder()
	{
		super("machine_grinder");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineGrinder();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineGrinder.class;
	}

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiType.MachineGrinder.getId();
	}
}
