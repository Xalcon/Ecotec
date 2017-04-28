package net.xalcon.ecotec.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetDynamicString;
import net.xalcon.ecotec.common.inventories.ContainerDsu;
import net.xalcon.ecotec.common.inventories.GuiElementContext;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

public class GuiDeepStorageUnit extends GuiBase<TileEntityDeepStorageUnit>
{
	public GuiDeepStorageUnit(GuiElementContext<TileEntityDeepStorageUnit> context)
	{
		super(new ContainerDsu(context), context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.widgets.add(new WidgetDynamicString(this.xSize / 2, 20, Minecraft.getMinecraft().fontRenderer,
				() -> itemHandler.getStackInSlot(0).toString()));
	}
}
