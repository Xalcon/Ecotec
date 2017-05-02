package net.xalcon.ecotec.common.tileentities.logistics;

import net.xalcon.ecotec.common.components.ComponentItemHandlerDSU;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderDeepStorageUnit;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

public class TileEntityDeepStorageUnit extends TileEntityBase
{
	public TileEntityDeepStorageUnit()
	{
		this.addComponent(new ComponentItemHandlerDSU());
		this.addComponent(new GuiProviderDeepStorageUnit());
	}
}
