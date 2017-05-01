package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraftforge.items.CapabilityItemHandler;
import net.xalcon.ecotec.common.components.ComponentItemHandlerDSU;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

public class TileEntityDeepStorageUnit extends TileEntityBase
{
	public TileEntityDeepStorageUnit()
	{
		this.addComponent(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, new ComponentItemHandlerDSU());
	}
}
