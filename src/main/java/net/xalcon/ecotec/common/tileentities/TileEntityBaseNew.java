package net.xalcon.ecotec.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.xalcon.ecotec.common.network.EcotecNetwork;
import net.xalcon.ecotec.common.network.PacketUpdateClientTileEntityCustom;
import net.xalcon.ecotec.core.IEcotecComponent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class TileEntityBaseNew extends TileEntity
{
	//region Capability System
	private Map<Capability<?>, IEcotecComponent> components = new HashMap<>();

	protected  <T> void addCapability(Capability<T> cap, T component)
	{
		if(!(component instanceof IEcotecComponent))
			throw new IllegalArgumentException("component needs to be an instance of IEcotecComponent");

		if(!this.components.containsKey(cap))
			this.components.put(cap, (IEcotecComponent) component);
	}

	@Override
	public boolean hasCapability(Capability<?> cap, @Nullable EnumFacing facing)
	{
		return this.components.containsKey(cap) || super.hasCapability(cap, facing);
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> cap, @Nullable EnumFacing facing)
	{
		IEcotecComponent ecoCap;
		return ((ecoCap = this.components.get(cap)) != null) ? (T)ecoCap : super.getCapability(cap, facing);
	}
	//endregion

	//region NBT Sync stuff
	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final void readFromNBT(NBTTagCompound compound)
	{
		this.readSyncNbt(compound, NbtSyncType.TILE);
	}

	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		this.writeSyncNbt(compound, NbtSyncType.TILE);
		return compound;
	}

	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_PARTIAL);
		return new SPacketUpdateTileEntity(this.pos, -1, compound);
	}

	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		this.readSyncNbt(packet.getNbtCompound(), NbtSyncType.NETWORK_SYNC_PARTIAL);
	}

	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final NBTTagCompound getUpdateTag()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
		return compound;
	}

	/**
	 * @deprecated if possible, use {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} instead
	 */
	@Deprecated
	@Override
	public final void handleUpdateTag(NBTTagCompound compound)
	{
		this.readSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
	}

	/**
	 * Reads stuff from nbt. will be used by the packet manager (i.e. after using sendUpdate())
	 * @param nbt the nbt to read from
	 * @param type sync type. Consider writing only important information on partial syncs
	 */
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		if(type.isFullSync())
			super.readFromNBT(nbt);

		for(IEcotecComponent cap : this.components.values())
			cap.readSyncNbt(nbt, type);
	}

	/**
	 * writes nbt data into the compound, depending on the sync type
	 * @param nbt the nbt to write into
	 * @param type sync type. Consider writing only important information on partial syncs
	 */
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		if(type.isFullSync())
			super.writeToNBT(nbt);

		for(IEcotecComponent cap : this.components.values())
			cap.writeSyncNbt(nbt, type);
	}

	/**
	 * Send an tile update to the client
	 * @param fullSync If true, the update will trigger a {@link NbtSyncType#NETWORK_SYNC_FULL}, otherwise {@link NbtSyncType#NETWORK_SYNC_PARTIAL}
	 */
	public final void sendUpdate(boolean fullSync)
	{
		if(this.world != null && !this.world.isRemote)
		{
			NBTTagCompound compound = new NBTTagCompound();
			this.writeSyncNbt(compound, fullSync ? NbtSyncType.NETWORK_SYNC_FULL : NbtSyncType.NETWORK_SYNC_PARTIAL);

			EcotecNetwork.getNetwork().sendToAllAround(new PacketUpdateClientTileEntityCustom(compound, this.getPos()),
					new NetworkRegistry.TargetPoint(this.getWorld().provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 64));
		}
	}

	/**
	 * Determines if this tile entity wants to save additional nbt to the an itemstack on harvest
	 * @return If true, the block class will call {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} with {@link NbtSyncType#BLOCK}.
	 */
	public boolean saveNbtOnDrop() { return false; }
	//endregion

}
