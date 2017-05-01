package net.xalcon.ecotec.api.components;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nonnull;

public interface IEcotecComponent<T>
{
	/**
	 * Will be called by the capability provider once the provider has been setup completly.
	 * In rare cases this can be called multiple times per lifecycle!
	 * (Especially if the provider is a tileentity and some mods mess with them - frame-mods anyone?)
	 * @param provider the provider that this component is assigned to
	 */
	default void initialize(ICapabilityProvider provider) { }

	/**
	 * the provider might be in a bad state, accessing the provider should be avoided
	 * until initialize is called again (it might never be called again tho :x)
	 */
	default void invalidate() { }

	/**
	 * Used to restore capability specific data on a sync or tile load
	 * @param nbt the nbt to read from
	 * @param type the sync type
	 */
	default void readSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }

	/**
	 * Used to store capability specific data on a sync or tile save
	 * @param nbt the nbt to write to
	 * @param type the sync type
	 */
	default void writeSyncNbt(@Nonnull NBTTagCompound nbt, @Nonnull NbtSyncType type) { }

	/**
	 * Returns the capability for this component
	 * @return the capability that was registered to forge
	 */
	Capability<T> getCapability();
}
