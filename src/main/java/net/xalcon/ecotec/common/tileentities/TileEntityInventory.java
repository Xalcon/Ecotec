package net.xalcon.ecotec.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileEntityInventory<T extends IItemHandler & INBTSerializable<NBTTagCompound>> extends TileEntityBase
{
	protected T inventory;

	protected TileEntityInventory(T inventory)
	{
		this.inventory = inventory;
	}

	@SuppressWarnings("UnusedReturnValue")
	public ItemStack insertItemStack(ItemStack itemStack)
	{
		return ItemHandlerHelper.insertItemStacked(this.inventory, itemStack, false);
	}

	//region NBT read/write
	@Override
	public void readSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.readSyncNbt(compound, type);

		if(type == NbtSyncType.TILE)
			this.inventory.deserializeNBT(compound.getCompoundTag("Items"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.writeSyncNbt(compound, type);

		// inventory sync is handled by minecraft
		// we dont need to write this info into a partial sync
		if(type == NbtSyncType.TILE)
			compound.setTag("Items", this.inventory.serializeNBT());
	}
	//endregion

	//region Capabilities
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <C> C getCapability(Capability<C> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory)
				: super.getCapability(capability, facing);
	}
	//endregion
}
