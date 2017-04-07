package net.xalcon.minefactory.common.inventory;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.inventory.slots.SlotMachineUpgrade;
import net.xalcon.minefactory.common.tileentities.TileEntityMachine;
import net.xalcon.minefactory.common.tileentities.TileEntityBase;

public class ContainerBase<T extends TileEntityBase> extends Container
{
	protected T tileEntity;
	protected InventoryPlayer inventoryPlayer;
	private static final int SLOT_SIZE = 18;
	private static final int ACTION_BAR_Y_OFFSET = 58;

	@SuppressWarnings("unchecked")
	public ContainerBase(GuiType.ContextInfo context)
	{

		// TODO: Refactor ContextInfo to allow for checked casting
		this(context.getPlayer().inventory, (T) context.getWorld().getTileEntity(context.getPos()));
	}

	public ContainerBase(InventoryPlayer inventoryPlayer, T tileEntity)
	{
		this.inventoryPlayer = inventoryPlayer;
		this.tileEntity = tileEntity;

		if(inventoryPlayer != null)
			bindPlayerInventory();

		if(tileEntity == null) return;

		if(tileEntity instanceof TileEntityMachine)
		{
			TileEntityMachine machine = (TileEntityMachine) this.tileEntity;
			this.addSlotToContainer(new SlotMachineUpgrade(machine, machine.getUpgradeSlotIndex(), 8, 54));
		}
	}

	public int getPlayerInventoryVerticalOffset() { return 84; }
	public int getPlayerInventoryHorizontalOffset() { return 8; }

	/**
	 * Binds the player inventory to the first 36 slots
	 * Player Inventory Slots: 0-35
	 */
	private void bindPlayerInventory()
	{
		int offsetX = getPlayerInventoryHorizontalOffset();
		int offsetY = getPlayerInventoryVerticalOffset();
		// bind action bar
		for (int x = 0; x < 9; x++)
			addSlotToContainer(new Slot(this.inventoryPlayer, x, offsetX + x * SLOT_SIZE, offsetY + ACTION_BAR_Y_OFFSET));

		// bind upper inventory
		for (int y = 0; y < 3; y++)
			for (int x = 0; x < 9; x++)
				addSlotToContainer(new Slot(this.inventoryPlayer, x + y * 9 + 9, offsetX + x * SLOT_SIZE, offsetY + y * SLOT_SIZE));
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
			if(this.tileEntity instanceof TileEntityBase)
			{
				TileEntityBase te = (TileEntityBase) this.tileEntity;
				containerInventorySize = te.getSizeInventory();
			}

			if (index < containerInventoryOffset)
			{
				// From Player inventory to container
				if (!this.mergeItemStack(itemstack1, containerInventoryOffset, this.inventorySlots.size(), false))
				{
					return ItemStack.EMPTY;
				}
			}
			else
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
			}
			else
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
