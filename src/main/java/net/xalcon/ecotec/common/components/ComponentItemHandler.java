package net.xalcon.ecotec.common.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.api.components.IEcotecComponent;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;

public class ComponentItemHandler extends ItemStackHandler implements IEcotecComponent
{
	public ComponentItemHandler(int size)
	{
		super(size);
	}

	@Override
	public void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		if(type != NbtSyncType.NETWORK_SYNC_PARTIAL)
			this.deserializeNBT(nbt.getCompoundTag("eco:items"));
	}

	@Override
	public void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type)
	{
		if(type != NbtSyncType.NETWORK_SYNC_PARTIAL)
			nbt.setTag("eco:items", this.serializeNBT());
	}
}
