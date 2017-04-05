package net.xalcon.minefactory.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineHarvester;

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
