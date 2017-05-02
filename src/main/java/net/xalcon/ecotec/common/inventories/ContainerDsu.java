package net.xalcon.ecotec.common.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

import javax.annotation.Nonnull;

public class ContainerDsu extends ContainerBase
{
	public ContainerDsu(EntityPlayer player, TileEntityDeepStorageUnit tileEntity)
	{
		super(player, tileEntity);
	}

	@Nonnull
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int containerInventoryOffset = this.inventoryPlayer != null ? 36 : 0;
			if (index < containerInventoryOffset)
			{
				// From Player inventory to guiprovider
				if (!this.mergeItemStack(itemstack1, containerInventoryOffset, containerInventoryOffset + 1, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else
			{
				// From guiprovider to player inventory
				if (!this.mergeItemStack(itemstack1, 0, containerInventoryOffset, false))
				{
					return ItemStack.EMPTY;
				}
			}


			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			} else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}
