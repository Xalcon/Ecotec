package net.xalcon.ecotec.common.inventories.itemstackhandler;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerDisenchanter extends ItemStackHandler
{
	public final static int SLOT_INDEX_INSERT_ITEM = 0;
	public final static int SLOT_INDEX_INSERT_BOOK = 1;
	public final static int SLOT_INDEX_OUTPUT_ITEM = 2;
	public final static int SLOT_INDEX_OUTPUT_BOOK = 3;

	public ItemStackHandlerDisenchanter()
	{
		super(4);
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
	{
		if(slot == SLOT_INDEX_INSERT_ITEM && stack.isItemEnchanted())
			return super.insertItem(slot, stack, simulate);
		else if(slot == SLOT_INDEX_INSERT_BOOK && stack.getItem() == Items.BOOK)
			return super.insertItem(slot, stack, simulate);
		return stack;
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(slot == SLOT_INDEX_OUTPUT_BOOK || slot == SLOT_INDEX_OUTPUT_ITEM)
			return super.extractItem(slot, amount, simulate);
		return ItemStack.EMPTY;
	}
}
