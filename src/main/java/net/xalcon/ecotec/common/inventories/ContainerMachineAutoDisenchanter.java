package net.xalcon.ecotec.common.inventories;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.common.inventories.slots.SlotOverrideInOut;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoDisenchanter;

public class ContainerMachineAutoDisenchanter extends ContainerBase<TileEntityMachineAutoDisenchanter>
{
	public ContainerMachineAutoDisenchanter(GuiElementContext<TileEntityMachineAutoDisenchanter> context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 0, 40, 30, true, true));
		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 1, 60, 30, true, true));
		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 2, 40, 60, false, true));
		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 3, 60, 60, false, true));
	}
}
