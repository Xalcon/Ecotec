package net.xalcon.ecotec.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.common.GuiType;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nullable;

public class ContainerBase<T extends TileEntityBase> extends Container
{
	private static final int SLOT_SIZE = 18;
	private static final int ACTION_BAR_Y_OFFSET = 58;
	private static final int PLAYER_INVENTORY_DEFAULT_VERTICAL_OFFSET = 84;
	private static final int PLAYER_INVENTORY_DEFAULT_HORIZONTAL_OFFSET = 8;

	protected T tileEntity;
	protected InventoryPlayer inventoryPlayer;

	public ContainerBase(GuiType.ContextInfo context)
	{
		this(context.getPlayer().inventory, context.getTileEntity());
	}

	public ContainerBase(@Nullable InventoryPlayer inventoryPlayer, T tileEntity)
	{
		this.inventoryPlayer = inventoryPlayer;
		this.tileEntity = tileEntity;

		if (inventoryPlayer != null)
			this.bindPlayerInventory();
	}

	protected int getPlayerInventoryVerticalOffset() { return PLAYER_INVENTORY_DEFAULT_VERTICAL_OFFSET; }

	protected int getPlayerInventoryHorizontalOffset() { return PLAYER_INVENTORY_DEFAULT_HORIZONTAL_OFFSET; }

	/**
	 * Binds the player inventory to the first 36 slots
	 * Player Inventory Slots: 0-35
	 */
	private void bindPlayerInventory()
	{
		int offsetX = this.getPlayerInventoryHorizontalOffset();
		int offsetY = this.getPlayerInventoryVerticalOffset();
		// bind action bar
		for (int x = 0; x < 9; x++)
			this.addSlotToContainer(new Slot(this.inventoryPlayer, x, offsetX + x * SLOT_SIZE, offsetY + ACTION_BAR_Y_OFFSET));

		// bind upper inventory
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				this.addSlotToContainer(new Slot(this.inventoryPlayer, x + y * 9 + 9, offsetX + x * SLOT_SIZE, offsetY + y * SLOT_SIZE));
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			int containerInventorySize = 0;
			int containerInventoryOffset = this.inventoryPlayer != null ? 36 : 0;

			if (this.tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null))
			{
				IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				//noinspection ConstantConditions
				containerInventorySize = itemHandler.getSlots();
			}

			if (index < containerInventoryOffset)
			{
				// From Player inventory to container
				if (!this.mergeItemStack(itemstack1, containerInventoryOffset, containerInventoryOffset + containerInventorySize, false))
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
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}
