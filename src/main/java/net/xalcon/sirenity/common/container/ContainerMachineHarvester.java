package net.xalcon.sirenity.common.container;

import com.sun.istack.internal.NotNull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.sirenity.common.GuiType;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineHarvester;

import javax.annotation.Nonnull;

public class ContainerMachineHarvester extends Container
{
	public ContainerMachineHarvester(GuiType.ContextInfo context)
	{
		TileEntityMachineHarvester te = (TileEntityMachineHarvester) context.getWorld().getTileEntity(context.getPos());
		IInventory playerInv = context.getPlayer().inventory;
		// Tile Entity, Slot 0-8, Slot IDs 0-8
		/*for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				this.addSlotToContainer(new Slot(te, x + y * 3, 62 + x * 18, 17 + y * 18));
			}
		}*/

		// Player Inventory, Slot 9-35, Slot IDs 9-35
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Player Inventory, Slot 0-8, Slot IDs 36-44
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
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
