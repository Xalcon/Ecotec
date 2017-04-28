package net.xalcon.ecotec.common.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.xalcon.ecotec.common.tileentities.agriculture.TileEntityMachineHarvester;

public class ContainerMachineHarvester extends ContainerBase<TileEntityMachineHarvester>
{
	public ContainerMachineHarvester(GuiElementContext<TileEntityMachineHarvester> context)
	{
		super(context);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}