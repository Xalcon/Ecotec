package net.xalcon.ecotec.common.inventories;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.common.inventories.slots.SlotOverrideInOut;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoEnchanter;

public class ContainerMachineAutoEnchanter extends ContainerBase<TileEntityMachineAutoEnchanter>
{
	public ContainerMachineAutoEnchanter(GuiElementContext<TileEntityMachineAutoEnchanter> context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 0, 40, 40, true, true));
		this.addSlotToContainer(new SlotOverrideInOut(itemHandler, 1, 60, 40, false, true));
	}
}
