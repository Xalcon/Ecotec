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
		this.readSyncNbt(compound, NbtSyncType.TILE);
	}

	@Override
	public final NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		this.writeSyncNbt(compound, NbtSyncType.TILE);
		return compound;
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_PARTIAL);
		return new SPacketUpdateTileEntity(this.pos, -1, compound);
	}

	@Override
	public final void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet)
	{
		this.readSyncNbt(packet.getNbtCompound(), NbtSyncType.NETWORK_SYNC_PARTIAL);
	}

	@Override
	public final NBTTagCompound getUpdateTag()
	{
		NBTTagCompound compound = new NBTTagCompound();
		this.writeSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
		return compound;
	}

	@Override
	public final void handleUpdateTag(NBTTagCompound compound)
	{
		this.readSyncNbt(compound, NbtSyncType.NETWORK_SYNC_FULL);
	}

	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		if(type.isFullSync())
			super.readFromNBT(nbt);
	}

	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
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

	/**
	 * Determines if this tile entity wants to save additional nbt to the an itemstack on harvest
	 * @return If true, the block class will call {@link #readSyncNbt(NBTTagCompound, NbtSyncType)} with {@link NbtSyncType#BLOCK}.
	 */
	public boolean saveNbtOnDrop() { return false; }
	//endregion


}
