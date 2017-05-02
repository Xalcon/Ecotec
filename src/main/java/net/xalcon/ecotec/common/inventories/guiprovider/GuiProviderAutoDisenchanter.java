package net.xalcon.ecotec.common.inventories.guiprovider;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.common.inventories.slots.SlotOverrideInOut;

public class GuiProviderAutoDisenchanter implements IGuiProvider
{
	private IItemHandler inventory;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.inventory = provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
	}

	@Override
	public void invalidate()
	{
		this.inventory = null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addWidgets(IGuiWidgetHandler widgetHandler) { }

	@Override
	public void addSlots(IContainerSlotHandler slotHandler)
	{
		if(this.inventory == null) return;
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 0, 40, 30, true, true));
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 1, 60, 30, true, true));
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 2, 40, 60, false, true));
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 3, 60, 60, false, true));
	}
}
