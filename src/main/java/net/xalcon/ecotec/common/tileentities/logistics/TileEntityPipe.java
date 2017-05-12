package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.EnumFacing;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.properties.EnumPipeConnection;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;
import sun.util.resources.cldr.ha.CalendarData_ha_Latn_NE;

import java.util.Arrays;

public class TileEntityPipe extends TileEntityBase
{
	private EnumPipeConnection[] connections = new EnumPipeConnection[]
			{
					EnumPipeConnection.DISCONNECTED,
					EnumPipeConnection.DISCONNECTED,
					EnumPipeConnection.DISCONNECTED,
					EnumPipeConnection.DISCONNECTED,
					EnumPipeConnection.DISCONNECTED,
					EnumPipeConnection.DISCONNECTED,
			};

	@Override
	public void onLoad()
	{
		super.onLoad();
		this.updateConnections();
	}

	public EnumPipeConnection getConnection(EnumFacing direction)
	{
		return this.connections[direction.getIndex()];
	}

	public boolean setConnection(EnumFacing direction, EnumPipeConnection connection)
	{
		if(this.connections[direction.getIndex()] != connection)
		{
			this.connections[direction.getIndex()] = connection;
			IBlockState state = this.getWorld().getBlockState(this.getPos());
			this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
			return true;
		}
		return false;
	}

	public boolean updateConnections()
	{
		boolean changed = false;
		for(EnumFacing facing : EnumFacing.values())
		{
			TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(facing));
			if(te instanceof TileEntityPipe)
			{
				if(this.setConnection(facing, EnumPipeConnection.CONNECTED))
					changed = true;
			}
			else
			{
				if(this.setConnection(facing, EnumPipeConnection.DISCONNECTED))
					changed = true;
			}
		}
		return changed;
	}

	@Override
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.readSyncNbt(nbt, type);
		int v = nbt.getInteger("eco:connections");
		for(int i = 0; i < 6; i++)
		{
			int c = (v >> (i * 3)) & 0b111;
			this.connections[i] = EnumPipeConnection.fromIndex(c);
		}
		this.markDirty();

		if(this.getWorld().isRemote)
		{
			IBlockState state = this.getWorld().getBlockState(this.getPos());
			this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 1);
		}
	}

	@Override
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.writeSyncNbt(nbt, type);
		int v = this.connections[5].getIndex() << (3 * 5)
				| this.connections[4].getIndex() << (3 * 4)
				| this.connections[3].getIndex() << (3 * 3)
				| this.connections[2].getIndex() << (3 * 2)
				| this.connections[1].getIndex() << 3
				| this.connections[0].getIndex();
		nbt.setInteger("eco:connections", v);
	}
}
