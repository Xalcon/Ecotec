package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;

public class GuiProviderFisher implements IGuiProvider
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
	}

	@Override
	public void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler)
	{
	}

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
		slotHandler.addSlot(new SlotItemHandler(this.inventory, 0, 80, 40));
	}
}
