package net.xalcon.ecotec.common.blocks.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiRegistry;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoSpawner;

import javax.annotation.Nullable;

public class BlockMachineAutoSpawner extends BlockMachineBase
{
	public BlockMachineAutoSpawner()
	{
		super("machine_auto_spawner");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineAutoSpawner();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineAutoSpawner.class;
	}

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiRegistry.MachineAutoSpawner;
	}
}
