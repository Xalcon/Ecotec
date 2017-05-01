package net.xalcon.ecotec.common.tileentities;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;

public interface IAutoRegisterTileEntity extends ITileEntityProvider
{
	Class<? extends TileEntity> getTileEntityClass();
}
