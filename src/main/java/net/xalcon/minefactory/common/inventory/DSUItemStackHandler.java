package net.xalcon.minefactory.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class DSUItemStackHandler implements IItemHandler, INBTSerializable<NBTTagCompound>
{
	private ItemStack storedItem;

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Item", storedItem.writeToNBT(new NBTTagCompound()));
		return nbt;
	}

	@Override
	public void deserializeNBT(@Nullable NBTTagCompound nbt)
	{
		if (nbt == null) return;
		NBTTagCompound itemTags = nbt.getCompoundTag("Item");
		this.storedItem = new ItemStack(itemTags);
	}

	@Override
	public int getSlots()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (slot != 1) return ItemStack.EMPTY;
		return this.storedItem;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return stack;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot)
	{
		return 0;
	}
}
