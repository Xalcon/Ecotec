package net.xalcon.ecotec.common.init;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.xalcon.ecotec.api.components.*;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;

import javax.annotation.Nullable;

public class ModCaps
{
	@CapabilityInject(IWorldInteractive.class)
	private final static Capability<IWorldInteractive> WORLD_INTERACTIVE_CAP = null;
	public static Capability<IWorldInteractive> getWorldInteractiveCap() { return WORLD_INTERACTIVE_CAP; }

	@CapabilityInject(IItemDropoff.class)
	private final static Capability<IItemDropoff> ITEM_DROPOFF_CAP = null;
	public static Capability<IItemDropoff> getItemDropoffCap() { return ITEM_DROPOFF_CAP; }

	@CapabilityInject(IBlockLocation.class)
	private final static Capability<IBlockLocation> BLOCK_LOCATION_CAP = null;
	public static Capability<IBlockLocation> getBlockLocationCap() { return BLOCK_LOCATION_CAP; }

	@CapabilityInject(IStateUpdatable.class)
	private final static Capability<IStateUpdatable> STATE_UPDATABLE_CAP = null;
	public static Capability<IStateUpdatable> getStateUpdatableCap() { return STATE_UPDATABLE_CAP; }

	@CapabilityInject(IGuiProvider.class)
	private final static Capability<IGuiProvider> GUI_PROVIDER_CAP = null;
	public static Capability<IGuiProvider> getGuiProviderCap() { return GUI_PROVIDER_CAP; }

	@CapabilityInject(IFluidItemInteraction.class)
	private final static Capability<IFluidItemInteraction> BUCKET_INTERACTION_CAP = null;
	public static Capability<IFluidItemInteraction> getBucketInteractionCap() { return BUCKET_INTERACTION_CAP; }

	public static void init()
	{
		CapabilityManager.INSTANCE.register(IWorldInteractive.class, new EcotecComponentStorage<>(), () -> null);
		CapabilityManager.INSTANCE.register(IItemDropoff.class, new EcotecComponentStorage<>(), () -> null);
		CapabilityManager.INSTANCE.register(IBlockLocation.class, new EcotecComponentStorage<>(), () -> null);
		CapabilityManager.INSTANCE.register(IStateUpdatable.class, new EcotecComponentStorage<>(), () -> null);
		CapabilityManager.INSTANCE.register(IGuiProvider.class, new EcotecComponentStorage<>(), () -> null);
		CapabilityManager.INSTANCE.register(IFluidItemInteraction.class, new EcotecComponentStorage<>(), () -> null);
	}

	private static class EcotecComponentStorage<T extends IEcotecComponent> implements Capability.IStorage<T>
	{
		@Nullable
		@Override
		public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side)
		{
			NBTTagCompound nbt = new NBTTagCompound();
			instance.writeSyncNbt(nbt, NbtSyncType.NETWORK_SYNC_FULL);
			return nbt;
		}

		@Override
		public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt)
		{
			if(nbt instanceof NBTTagCompound)
				instance.readSyncNbt((NBTTagCompound) nbt, NbtSyncType.NETWORK_SYNC_FULL);
		}
	}
}
