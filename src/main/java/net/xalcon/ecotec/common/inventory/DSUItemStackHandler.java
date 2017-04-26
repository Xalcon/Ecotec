package net.xalcon.ecotec.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class DSUItemStackHandler implements IItemHandler, INBTSerializable<NBTTagCompound>
{
	private ItemStack storedItem = ItemStack.EMPTY;
	private int count;

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Item", this.storedItem.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("ItemCount", this.count);
		this.ensureEmpty();
		return nbt;
	}

	@Override
	public void deserializeNBT(@Nullable NBTTagCompound nbt)
	{
		if (nbt == null) return;
		NBTTagCompound itemTags = nbt.getCompoundTag("Item");
		this.storedItem = new ItemStack(itemTags);
		this.count = nbt.getInteger("ItemCount");
		this.ensureEmpty();
	}

	@Override
	public int getSlots()
	{
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (slot != 0) return ItemStack.EMPTY;
		ItemStack outStack = this.storedItem.copy();
		outStack.setCount(this.count);
		return outStack;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		if(this.storedItem.isEmpty())
		{
			if(!simulate)
			{
				this.storedItem = stack.copy();
				this.count = stack.getCount();
				stack.setCount(0);
			}
			return ItemStack.EMPTY;
		}
		else if(this.storedItem.isItemEqual(stack))
		{
			if(!simulate)
			{
				this.count += stack.getCount();
				stack.setCount(0);
			}
			return ItemStack.EMPTY;
		}
		return stack;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(this.storedItem.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStack = this.storedItem.copy();
		itemStack.setCount(Math.min(this.count, amount));
		if(!simulate)
			this.count -= itemStack.getCount();

		this.ensureEmpty();

		return itemStack;
	}

	private void ensureEmpty()
	{
		if(this.count == 0)
		{
			this.storedItem = ItemStack.EMPTY;
		}
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return 1;
	}
}
