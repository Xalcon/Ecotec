package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.xalcon.ecotec.common.blocks.properties.EnumPipeConnection;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

public class TileEntityFluidPipe extends TileEntityBase
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

	public EnumPipeConnection getConnection(EnumFacing direction)
	{
		return this.connections[direction.getIndex()];
	}

	public void setConnection(EnumFacing direction, EnumPipeConnection connection)
	{
		if(this.connections[direction.getIndex()] != connection)
		{
			this.connections[direction.getIndex()] = connection;
			IBlockState state = this.getWorld().getBlockState(this.getPos());
			this.getWorld().notifyBlockUpdate(this.getPos(), state, state, 3);
		}
	}
}
