package net.xalcon.minefactory.common.inventory;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineAutoSpawner;

public class ContainerMachineAutoSpawner extends ContainerBase<TileEntityMachineAutoSpawner>
{
	public ContainerMachineAutoSpawner(GuiType.ContextInfo context)
	{
		super(context);
		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(itemHandler, 0, 79, 34));
	}
}
