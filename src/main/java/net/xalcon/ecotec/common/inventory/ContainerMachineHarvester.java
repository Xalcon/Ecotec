package net.xalcon.ecotec.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

public class ContainerMachineHarvester extends ContainerBase<TileEntityMachineHarvester>
{
	public ContainerMachineHarvester(GuiType.ContextInfo context)
	{
		super(context);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
