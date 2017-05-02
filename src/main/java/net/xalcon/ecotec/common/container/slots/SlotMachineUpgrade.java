package net.xalcon.ecotec.common.container.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.init.ModItems;

public class SlotMachineUpgrade extends Slot
{
	public SlotMachineUpgrade(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() == ModItems.MachineRangeUpgrade;
	}

	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}
