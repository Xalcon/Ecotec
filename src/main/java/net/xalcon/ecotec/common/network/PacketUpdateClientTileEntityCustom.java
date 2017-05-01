package net.xalcon.ecotec.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBaseNew;

/**
 * This packet is used for small and frequent tile entity updates that only do partial updates.
 * For example: energy level changed, machine progress, etc
 */
public class PacketUpdateClientTileEntityCustom implements IMessage
{
	private NBTTagCompound data;
	private BlockPos tileEntityPos;

	// A default constructor is always required
	public PacketUpdateClientTileEntityCustom() { }

	public PacketUpdateClientTileEntityCustom(NBTTagCompound data, BlockPos pos)
	{
		this.data = data;
		this.tileEntityPos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		PacketBuffer buffer = new PacketBuffer(buf);
		try
		{
			this.tileEntityPos = buffer.readBlockPos();
			this.data = buffer.readCompoundTag();
		}
		catch(Exception e)
		{
			Ecotec.Log.error("Something went wrong while processing packet", e);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		PacketBuffer buffer = new PacketBuffer(buf);
		buffer.writeBlockPos(this.tileEntityPos);
		buffer.writeCompoundTag(this.data);
	}

	public static class Handler implements IMessageHandler<PacketUpdateClientTileEntityCustom, IMessage>
	{
		@Override
		public IMessage onMessage(PacketUpdateClientTileEntityCustom message, MessageContext ctx)
		{
			// we are running in the network thread - delegate the message handling to the main thread
			Minecraft.getMinecraft().addScheduledTask(() -> this.handleMessage(message, ctx));
			return null;
		}

		@SideOnly(Side.CLIENT)
		private void handleMessage(PacketUpdateClientTileEntityCustom message, MessageContext ctx)
		{
			World world = Minecraft.getMinecraft().world;
			if(world != null)
			{
				TileEntity tile = world.getTileEntity(message.tileEntityPos);
				if(tile instanceof TileEntityBaseNew)
				{
					((TileEntityBaseNew) tile).readSyncNbt(message.data, NbtSyncType.NETWORK_SYNC_PARTIAL);
				}
			}
		}
	}
}
