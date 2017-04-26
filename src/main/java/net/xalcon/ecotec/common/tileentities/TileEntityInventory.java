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
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.inventory.deserializeNBT(compound.getCompoundTag("Items"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound.setTag("Items", this.inventory.serializeNBT());
		return super.writeToNBT(compound);
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
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory)
				: super.getCapability(capability, facing);
	}
	//endregion
}
