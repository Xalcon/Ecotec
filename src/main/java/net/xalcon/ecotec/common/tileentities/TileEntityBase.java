package net.xalcon.ecotec.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.network.EcotecNetwork;
import net.xalcon.ecotec.common.network.PacketUpdateClientTileEntityCustom;

import javax.annotation.Nonnull;

public abstract class TileEntityBase extends TileEntity
{
	private String customDisplayName;

	public void setCustomDisplayName(String customName)
	{
		this.customDisplayName = customName;
	}

	public String getCustomDisplayName()
	{
		return this.customDisplayName;
	}

	public abstract String getUnlocalizedName();

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return this.customDisplayName != null
				? new TextComponentString(this.customDisplayName)
				: new TextComponentTranslation(this.getUnlocalizedName() + ".name");
	}

	//region NBT Sync stuff
	@Override
	public final void readFromNBT(NBTTagCompound compound)
	{
		Ecotec.Log.info("readFromNBT() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		this.readSyncNbt(compound, NbtSyncType.TILE);
	}

	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		Ecotec.Log.info("writeToNBT() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		this.writeSyncNbt(compound, NbtSyncType.TILE);
		return compound;
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket()
	{
		Ecotec.Log.info("getUpdatePacket() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_PARTIAL);
		return new SPacketUpdateTileEntity(this.pos, -1, compound);
	}

	@Override
	public final void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		Ecotec.Log.info("onDataPacket() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		this.readSyncNbt(packet.getNbtCompound(), NbtSyncType.NETWORK_SYNC_PARTIAL);
	}

	@Override
	public final NBTTagCompound getUpdateTag()
	{
		Ecotec.Log.info("getUpdateTag() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
		return compound;
	}

	@Override
	public final void handleUpdateTag(NBTTagCompound compound)
	{
		Ecotec.Log.info("handleUpdateTag() - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		this.readSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
	}

	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		Ecotec.Log.info("readSyncNbt("+type+") - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		if(type.isFullSync())
			super.readFromNBT(nbt);
	}

	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		Ecotec.Log.info("writeSyncNbt("+type+") - " + (this.world == null ? "SERVER???" : this.world.isRemote ? "client" : "server"));
		if(type.isFullSync())
			super.writeToNBT(nbt);
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
	//endregion

	/**
	 * Determines if this tile entity wants to save additional nbt to the an itemstack on harvest
	 * @return If true, the block class will call {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} with {@link NbtSyncType#BLOCK}.
	 */
	public boolean saveNbtOnDrop() { return false; }

	public enum NbtSyncType
	{
		/**
		 * PARTIAL READ/WRITE (NOT YET IMPLEMENTED)
		 * For reads: the block was placed in the world (probably from an item stack with nbt attached)
		 * For writes: the block is being stored into an itemstack, store all relevant data for a later read
		 * The tile needs to override {@link #saveNbtOnDrop()} for saving to occur.
		 */
		BLOCK(false),
		/**
		 * FULL READ/WRITE
		 * For reads: tile entity is being loaded from disk
		 * For writes: tile is going to get saved to disk
		 */
		TILE(true),
		/**
		 * FULL READ/WRITE
		 * For reads: Block was updated, write all
		 * For writes: Block was updated, read back everything
		 */
		NETWORK_SYNC_FULL(true),
		/**
		 * PARTIAL READ/WRITE
		 * Small update, only write changed data if possible
		 * Either issued manually by {@link #sendUpdate(boolean)} or by {@link net.minecraft.world.World#notifyBlockUpdate(BlockPos, IBlockState, IBlockState, int)}
		 */
		NETWORK_SYNC_PARTIAL(false);

		private final boolean isFullSync;

		public boolean isFullSync() { return this.isFullSync; }

		NbtSyncType(boolean isFullSync)
		{
			this.isFullSync = isFullSync;
		}
	}
}
