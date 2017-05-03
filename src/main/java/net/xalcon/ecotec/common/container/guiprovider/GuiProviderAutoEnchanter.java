package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.common.container.slots.SlotOverrideInOut;

public class GuiProviderAutoEnchanter implements IGuiProvider
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
	public void addWidgets(EntityPlayer player, IGuiWidgetHandler widgetHandler) { }

	@Override
	public void addSlots(EntityPlayer player, IContainerSlotHandler slotHandler)
	{
		if(this.inventory == null) return;
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 0, 40, 40, true, true));
		slotHandler.addSlot(new SlotOverrideInOut(this.inventory, 1, 60, 40, false, true));
	}
}
