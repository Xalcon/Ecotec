package net.xalcon.ecotec.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.client.gui.widgets.WidgetDynamicString;
import net.xalcon.ecotec.common.inventories.ContainerDsu;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

public class GuiDeepStorageUnit extends GuiBase<TileEntityDeepStorageUnit>
{
	public GuiDeepStorageUnit(EntityPlayer player, TileEntityDeepStorageUnit tileEntity)
	{
		super(new ContainerDsu(player, tileEntity), player.inventory, tileEntity);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		if(itemHandler != null)
		{
			this.widgets.add(new WidgetDynamicString(this.xSize / 2, 20, Minecraft.getMinecraft().fontRenderer,
					() ->
					{
						ItemStack itemStack = itemHandler.getStackInSlot(0);
						return itemStack.getCount() + "x " + I18n.format(itemStack.getUnlocalizedName() + ".name");
					}));
		}
	}
}
