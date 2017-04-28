package net.xalcon.ecotec.common.inventories.slots;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotDSUOutputOnly extends SlotItemHandler
{
	public SlotDSUOutputOnly(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return false;
	}

	@Nonnull
	@Override
	public ItemStack decrStackSize(int amount)
	{
		return super.decrStackSize(amount);
	}
}
