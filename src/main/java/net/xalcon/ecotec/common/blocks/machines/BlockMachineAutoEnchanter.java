package net.xalcon.ecotec.common.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiRegistry;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoEnchanter;

import javax.annotation.Nullable;

public class BlockMachineAutoEnchanter extends BlockMachineBase
{

	public BlockMachineAutoEnchanter()
	{
		super("machine_auto_enchanter");
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineAutoEnchanter.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineAutoEnchanter();
	}

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiRegistry.MachineAutoEnchanter;
	}
}
