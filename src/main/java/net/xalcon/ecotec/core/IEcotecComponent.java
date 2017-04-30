package net.xalcon.ecotec.core;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;

public interface IEcotecComponent
{
	/**
	 * Used to restore capability specific data on a sync or tile load
	 * @param nbt the nbt to read from
	 * @param type the sync type
	 */
	void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type);

	/**
	 * Used to store capability specific data on a sync or tile save
	 * @param nbt the nbt to write to
	 * @param type the sync type
	 */
	void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type);
}
