package net.xalcon.ecotec.common.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.xalcon.ecotec.common.inventories.slots.SlotDSUOutputOnly;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

import javax.annotation.Nonnull;

public class ContainerDsu extends ContainerBase<TileEntityDeepStorageUnit>
{
	public ContainerDsu(GuiElementContext<TileEntityDeepStorageUnit> context)
	{
		super(context);

		IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

		this.addSlotToContainer(new SlotItemHandler(itemHandler, 1, 60, 30));
		this.addSlotToContainer(new SlotDSUOutputOnly(itemHandler, 2, 95, 30));
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
				// From Player inventory to container
				if (!this.mergeItemStack(itemstack1, containerInventoryOffset, containerInventoryOffset + 1, false))
				{
					return ItemStack.EMPTY;
				}
			} else
			{
				// From container to player inventory
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
