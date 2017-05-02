package net.xalcon.ecotec.common.container.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotOverrideInOut extends SlotItemHandler
{
	private boolean allowIn;
	private boolean allowOut;

	public SlotOverrideInOut(IItemHandler itemHandler, int index, int xPosition, int yPosition, boolean allowIn, boolean allowOut)
	{
		super(itemHandler, index, xPosition, yPosition);
		this.allowIn = allowIn;
		this.allowOut = allowOut;
	}

	@Override
	public boolean isItemValid(@Nonnull ItemStack stack)
	{
		return this.allowIn;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn)
	{
		return this.allowOut;
	}

	@Nonnull
	@Override
	public ItemStack decrStackSize(int amount)
	{
		ItemStack itemStack = this.getItemHandler().getStackInSlot(this.getSlotIndex());
		ItemStack output = itemStack.splitStack(amount);
		((IItemHandlerModifiable)this.getItemHandler()).setStackInSlot(this.getSlotIndex(), itemStack);
		return output;
	}
}
