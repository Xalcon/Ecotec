package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.common.inventories.DSUItemStackHandler;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;
import net.xalcon.ecotec.common.tileentities.TileEntityInventory;

public class TileEntityDeepStorageUnit extends TileEntityInventory<DSUItemStackHandler>
{
	public TileEntityDeepStorageUnit()
	{
		super(new DSUItemStackHandler());
		this.inventory.setTile(this);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "deep_storage_unit";
	}

	@Override
	public ItemStack insertItemStack(ItemStack itemStack)
	{
		return this.inventory.insertItem(0, itemStack, false);
	}

	@Override
	public void readSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.readSyncNbt(compound, type);

		// The super class only saves the inventory on full sync
		// Normally, the gui container syncs each single slot on change, but because our DSU container
		// shouldnt access the mega slot, the it will never sync it.
		// Due to this, we need to handle updates to the DSU item handler ourselves.
		// the DSU item handler will call sendUpdate(false), so we just need to write the data
		if(!type.isFullSync())
			this.inventory.deserializeNBT(compound.getCompoundTag("Items"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.writeSyncNbt(compound, type);

		// see readSyncNbt()!
		if(!type.isFullSync())
			compound.setTag("Items", this.inventory.serializeNBT());
	}
}
