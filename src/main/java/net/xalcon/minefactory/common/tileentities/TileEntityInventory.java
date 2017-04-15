package net.xalcon.minefactory.common.tileentities;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.Nullable;
import java.util.List;

public abstract class TileEntityInventory extends TileEntityBase
{
	protected ItemStackHandler inventory;

	protected TileEntityInventory(int inventorySize)
	{
		this.inventory = new ItemStackHandler(inventorySize);
	}

	public ItemStack insertItemStack(ItemStack itemStack)
	{
		return ItemHandlerHelper.insertItemStacked(this.inventory, itemStack, false);
	}

	//region NBT read/write
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		inventory.deserializeNBT(compound.getCompoundTag("Items"));
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
