package net.xalcon.ecotec.common.inventories.guiprovider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetDynamicString;
import net.xalcon.ecotec.common.inventories.slots.SlotDSUOutputOnly;

public class GuiProviderDeepStorageUnit implements IGuiProvider
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
	public void addWidgets(IGuiWidgetHandler widgetHandler)
	{
		if(this.inventory == null) return;
		widgetHandler.addWidget(new WidgetDynamicString(88, 20, Minecraft.getMinecraft().fontRenderer,
		() ->
		{
			ItemStack itemStack = this.inventory.getStackInSlot(0);
			return itemStack.getCount() + "x " + I18n.format(itemStack.getUnlocalizedName() + ".name");
		}));
	}

	@Override
	public void addSlots(IContainerSlotHandler slotHandler)
	{
		if(this.inventory == null) return;
		slotHandler.addSlot(new SlotItemHandler(this.inventory, 1, 60, 30));
		slotHandler.addSlot(new SlotDSUOutputOnly(this.inventory, 2, 95, 30));
	}
}
