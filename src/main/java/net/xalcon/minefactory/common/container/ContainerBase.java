package net.xalcon.minefactory.common.container;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.xalcon.minefactory.common.GuiType;
import net.xalcon.minefactory.common.container.slots.SlotMachineUpgrade;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMFBase;
import net.xalcon.minefactory.common.tileentities.machines.TileEntityMachineRancher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ContainerBase<T extends TileEntityMFBase> extends Container
{
	protected T tileEntity;
	protected InventoryPlayer inventoryPlayer;
	private static final int SLOT_SIZE = 18;
	private static final int ACTION_BAR_Y_OFFSET = 58;

	public ContainerBase(GuiType.ContextInfo context)
	{
		//noinspection unchecked
		this(context.getPlayer().inventory, (T) context.getWorld().getTileEntity(context.getPos()));
	}

	public ContainerBase(@Nullable InventoryPlayer inventoryPlayer, @Nullable T tileEntity)
	{
		this.inventoryPlayer = inventoryPlayer;
		this.tileEntity = tileEntity;

		if(inventoryPlayer != null)
			bindPlayerInventory();

		if(tileEntity == null) return;

		if(tileEntity instanceof TileEntityMachineBase)
		{
			TileEntityMachineBase machine = (TileEntityMachineBase) this.tileEntity;
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

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn)
	{
		return true;
	}
}
