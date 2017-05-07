package net.xalcon.ecotec.common.blocks.magic;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.magic.TileEntityMachineAutoDisenchanter;

public class BlockMachineAutoDisenchanter extends BlockMachineBase
{
	public BlockMachineAutoDisenchanter()
	{
		super(Material.IRON, "machine_auto_disenchanter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineAutoDisenchanter();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineAutoDisenchanter.class;
	}
}
