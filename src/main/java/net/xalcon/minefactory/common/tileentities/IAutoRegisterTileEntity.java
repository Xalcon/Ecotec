package net.xalcon.minefactory.common.tileentities;

import net.minecraft.tileentity.TileEntity;

public interface IAutoRegisterTileEntity
{
	Class<? extends TileEntity> getTileEntityClass();
	String getTileEntityRegistryName();
}
