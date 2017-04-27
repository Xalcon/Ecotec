package net.xalcon.ecotec.common.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
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
				: new TextComponentTranslation("tile." + this.getUnlocalizedName());
	}

	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		this.writeSyncNbt(compound, NbtSyncType.TILE);
		return compound;
	}

	@Override
	public final void readFromNBT(NBTTagCompound compound)
	{
		this.readSyncNbt(compound, NbtSyncType.TILE);
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC);
		return new SPacketUpdateTileEntity(this.pos, -1, compound);
	}

	@Override
	public final void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet){
		this.readSyncNbt(packet.getNbtCompound(), NbtSyncType.NETWORK_SYNC);
	}

	@Override
	public final NBTTagCompound getUpdateTag(){
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC);
		return compound;
	}

	@Override
	public final void handleUpdateTag(NBTTagCompound compound){
		this.readSyncNbt(compound, NbtSyncType.NETWORK_SYNC);
	}

	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		Ecotec.Log.info("readSyncNbt() - " + type);
		if(type != NbtSyncType.BLOCK)
			super.readFromNBT(nbt);
	}

	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		Ecotec.Log.info("writeSyncNbt() - " + type);
		if(type != NbtSyncType.BLOCK)
			super.writeToNBT(nbt);
	}

	public final void sendUpdate()
	{
		if(this.world != null && !this.world.isRemote)
		{
			NBTTagCompound compound = new NBTTagCompound();
			this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC);

			EcotecNetwork.getNetwork().sendToAllAround(new PacketUpdateClientTileEntityCustom(compound, this.getPos()),
					new NetworkRegistry.TargetPoint(this.getWorld().provider.getDimension(), this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), 64));
		}
	}

	public enum NbtSyncType
	{
		/**
		 * PARTIAL READ/WRITE (NOT YET IMPLEMENTED)
		 * TODO: Implement this
		 * For reads: the block was placed in the world (probably from an item stack with nbt attached)
		 * For writes: the block is being stored into an itemstack, store all relevant data for a later read
		 */
		BLOCK,
		/**
		 * FULL READ/WRITE
		 * For reads: tile entity is being loaded from disk (or got send from server because a block update occured)
		 * For writes: tile is going to get saved to disk (or being send to the client because a block update occured)
		 */
		TILE,
		/**
		 * PARTIAL READ/WRITE
		 * Small update, only write changed data if possible
		 */
		NETWORK_SYNC
	}
}
