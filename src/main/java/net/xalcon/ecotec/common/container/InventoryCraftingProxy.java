package net.xalcon.ecotec.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class InventoryCraftingProxy<T extends IItemHandler & IItemHandlerModifiable> extends InventoryCrafting
{
	private final T itemHandler;

	public InventoryCraftingProxy(T itemHandler, int width, int height)
	{
		super(null, width, height);
		this.itemHandler = itemHandler;
	}

	@Override
	public int getSizeInventory()
	{
		return this.itemHandler.getSlots();
	}

	@Override
	public boolean isEmpty()
	{
		int c = this.getSizeInventory();
		for (int i = 0; i < c; i++)
			if (!this.itemHandler.getStackInSlot(i).isEmpty())
				return false;
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.itemHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack getStackInRowAndColumn(int row, int column)
	{
		return this.itemHandler.getStackInSlot(row + column * this.getWidth());
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		ItemStack outStack = this.itemHandler.getStackInSlot(index);
		this.itemHandler.setStackInSlot(index, ItemStack.EMPTY);
		return outStack;
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return this.itemHandler.extractItem(index, count, false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		this.itemHandler.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return this.itemHandler.getSlotLimit(0);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return super.isUsableByPlayer(player);
	}
}
