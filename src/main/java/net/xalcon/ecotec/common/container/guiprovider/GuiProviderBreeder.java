package net.xalcon.ecotec.common.container.guiprovider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.api.components.IGuiProvider;
import net.xalcon.ecotec.api.components.wrappers.IContainerSlotHandler;
import net.xalcon.ecotec.api.components.wrappers.IGuiWidgetHandler;

public class GuiProviderBreeder implements IGuiProvider
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
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				slotHandler.addSlot(new SlotItemHandler(this.inventory, x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}
}
