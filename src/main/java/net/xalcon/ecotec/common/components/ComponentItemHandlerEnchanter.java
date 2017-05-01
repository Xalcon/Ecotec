package net.xalcon.ecotec.common.components;

import com.ibm.icu.impl.Assert;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ComponentItemHandlerEnchanter extends ComponentItemHandler
{
	public final static int SLOT_INDEX_INSERT = 0;
	public final static int SLOT_INDEX_OUTPUT = 1;

	public ComponentItemHandlerEnchanter()
	{
		super(2);
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
	{
		if(slot == SLOT_INDEX_INSERT && stack.isItemEnchantable())
			return super.insertItem(slot, stack, simulate);
		return stack;
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(slot == SLOT_INDEX_OUTPUT)
			return super.extractItem(slot, amount, simulate);
		return ItemStack.EMPTY;
	}
}
