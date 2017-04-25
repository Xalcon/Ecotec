package net.xalcon.ecotec.common.blocks.agriculture;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

import javax.annotation.Nullable;

public class BlockMachineHarvester extends BlockMachineBase
{
	public BlockMachineHarvester()
	{
		super(Material.IRON, "machine_harvester");
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

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiType.MachineHarvester.getId();
	}
}
