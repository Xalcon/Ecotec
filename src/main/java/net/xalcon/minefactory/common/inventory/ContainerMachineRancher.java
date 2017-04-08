package net.xalcon.minefactory.common.inventory;

import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

public class ContainerMachineRancher extends ContainerBase<TileEntityMachineRancher>
{
	public ContainerMachineRancher(GuiType.ContextInfo context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				this.addSlotToContainer(new SlotItemHandler(itemHandler, x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}
}
