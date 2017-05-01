package net.xalcon.ecotec.common.init;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.xalcon.ecotec.api.components.IItemDropoff;
import net.xalcon.ecotec.api.components.IWorldInteractive;
import net.xalcon.ecotec.common.components.ComponentItemDropoff;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.core.IEcotecComponent;

import javax.annotation.Nullable;

public class ModCaps
{
	@CapabilityInject(IWorldInteractive.class)
	public final static Capability<IWorldInteractive> WORLD_INTERACTIVE_CAP = null;

	@CapabilityInject(IItemDropoff.class)
	public final static Capability<IItemDropoff> ITEM_DROPOFF_CAP = null;

	public static void init()
	{
		CapabilityManager.INSTANCE.register(IWorldInteractive.class, new EcotecComponentStorage<>(), () -> new ComponentWorldInteractiveFrontal(1));
		CapabilityManager.INSTANCE.register(IItemDropoff.class, new EcotecComponentStorage<>(), () -> null);
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
