package net.xalcon.ecotec.common.components;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.xalcon.ecotec.api.components.IEcotecComponent;
import net.xalcon.ecotec.api.components.IStateUpdatable;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;

public class ComponentItemHandlerDSU implements IItemHandler, IItemHandlerModifiable, IEcotecComponent<IItemHandler>
{
	private final static int SLOT_INDEX_MAIN = 0;
	private final static int SLOT_INDEX_VIRTUAL_INPUT = 1;
	private final static int SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY = 2;

	private ItemStack storedItem = ItemStack.EMPTY;
	private int count;
	private IStateUpdatable updatable;

	//region IItemHandler & IItemHandlerModifiable implementation
	@Override
	public int getSlots()
	{
		return 2;
	}

	@Nonnull
	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if(slot == SLOT_INDEX_MAIN)
		{
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(this.count);
			return outStack;
		}
		else if(slot == SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY)
		{
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(Math.min(this.count, outStack.getMaxStackSize()));
			return outStack;
		}
		return ItemStack.EMPTY;
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
	{
		if(this.storedItem.isEmpty())
		{
			if(!simulate)
			{
				this.storedItem = stack.copy();
				this.count = stack.getCount();
				stack.setCount(0);
				this.onContentsChanged();
			}
			return ItemStack.EMPTY;
		}
		else if(this.storedItem.isItemEqual(stack))
		{
			if(!simulate)
			{
				this.count += stack.getCount();
				stack.setCount(0);
				this.onContentsChanged();
			}
			return ItemStack.EMPTY;
		}
		return stack;
	}


	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if(this.storedItem.isEmpty()) return ItemStack.EMPTY;

		ItemStack itemStack = this.storedItem.copy();
		itemStack.setCount(Math.min(this.count, amount));
		if(!simulate)
		{
			this.count -= itemStack.getCount();
			this.onContentsChanged();
		}

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
		return slot == SLOT_INDEX_MAIN ? Integer.MAX_VALUE : this.storedItem.getMaxStackSize();
	}

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack)
	{
		if(slot == SLOT_INDEX_MAIN) return;

		if(slot == SLOT_INDEX_VIRTUAL_INPUT && !stack.isEmpty())
		{
			if(this.storedItem.isEmpty())
			{
				this.storedItem = stack.copy();
				this.count = stack.getCount();
				this.onContentsChanged();
			}
			else if(this.storedItem.isItemEqual(stack))
			{
				this.count += stack.getCount();
				this.onContentsChanged();
			}
		}
		else if(slot == SLOT_INDEX_VIRTUAL_OUTPUT_GUI_ONLY)
		{
			if(!stack.isEmpty()) return;
			ItemStack outStack = this.storedItem.copy();
			outStack.setCount(Math.min(this.count, outStack.getMaxStackSize()));
			this.count -= outStack.getCount();
		}
	}

	private void onContentsChanged()
	{
		if(this.updatable != null)
			this.updatable.scheduleUpdate();
	}
	//endregion

	//region IEcotecComponent implementation@Override
	public void initialize(ICapabilityProvider provider)
	{
		this.updatable = provider.getCapability(ModCaps.getStateUpdatableCap(), null);
	}

	@Override
	public void invalidate()
	{
		this.updatable = null;
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound itemsNbt = nbt.getCompoundTag("Items");
		NBTTagCompound itemStackTags = itemsNbt.getCompoundTag("Item");
		this.storedItem = new ItemStack(itemStackTags);
		this.count = itemsNbt.getInteger("ItemCount");
		this.ensureEmpty();
	}

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		NBTTagCompound itemsNbt = new NBTTagCompound();
		nbt.setTag("Items", itemsNbt);
		itemsNbt.setTag("Item", this.storedItem.writeToNBT(new NBTTagCompound()));
		itemsNbt.setInteger("ItemCount", this.count);
	}

	@Override
	public Capability<IItemHandler> getCapability()
	{
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}
	//endregion
}
