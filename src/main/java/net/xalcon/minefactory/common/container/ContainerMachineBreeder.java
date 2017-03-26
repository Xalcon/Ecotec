package net.xalcon.minefactory.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineBreeder;

import javax.annotation.Nonnull;

public class ContainerMachineBreeder extends ContainerBase
{
	public ContainerMachineBreeder(GuiType.ContextInfo context)
	{
		super(context.getPlayer().inventory, (TileEntityMachineBreeder) context.getWorld().getTileEntity(context.getPos()));
		TileEntityMachineBreeder tile = (TileEntityMachineBreeder) context.getWorld().getTileEntity(context.getPos());

		for(int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 3; x++)
			{
				this.addSlotToContainer(new Slot(tile, 1 + x + y * 3, 61 + x * 18, 16 + y * 18));
			}
		}
	}

	@Nonnull
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < 0) {
				// From TE Inventory to Player Inventory
				if (!this.mergeItemStack(current, 9, 45, true))
					return ItemStack.EMPTY;
			} else {
				// From Player Inventory to TE Inventory
				if (!this.mergeItemStack(current, 0, 36, false))
					return ItemStack.EMPTY;
			}

			if (current.getCount() == 0)
				slot.putStack(ItemStack.EMPTY);
			else
				slot.onSlotChanged();

			if (current.getCount() == previous.getCount())
				return ItemStack.EMPTY;
			slot.onTake(playerIn, current);
		}
		return previous;
	}
}
