package net.xalcon.minefactory.common.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileEntityMachineBase extends TileEntityWithInventory
{
	private int[] SLOTS;

	public TileEntityMachineBase(int inventorySize)
	{
		this.inventory.add(ItemStack.EMPTY);
		SLOTS = new int[inventorySize];
		for(int i = 0; i < inventorySize; i++)
		{
			this.inventory.add(ItemStack.EMPTY);
			SLOTS[i] = i + 1;
		}
	}

	public int getUpgradeSlotIndex()
	{
		return 0;
	}

	public ItemStack getUpgradeSlotItemStack()
	{
		return this.inventory.get(this.getUpgradeSlotIndex());
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(super.getUpdateTag());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.getNbtCompound());
	}

	// ISidedInventory implementation
	@Override
	@Nonnull
	public int[] getSlotsForFace(@Nonnull EnumFacing side)
	{
		return this.SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, @Nonnull ItemStack itemStackIn, @Nonnull EnumFacing direction)
	{
		return index != this.getUpgradeSlotIndex() && this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, @Nonnull ItemStack stack, @Nonnull EnumFacing direction)
	{
		return this.getUpgradeSlotIndex() != index;
	}
}
