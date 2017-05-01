package net.xalcon.ecotec.common.tileentities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public enum NbtSyncType
{
	/**
	 * PARTIAL READ/WRITE (NOT YET IMPLEMENTED)
	 * For reads: the block was placed in the world (probably from an item stack with nbt attached)
	 * For writes: the block is being stored into an itemstack, store all relevant data for a later read
	 * The tile needs to override {@link TileEntityBase#saveNbtOnDrop()} for saving to occur.
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
	 * Small scheduleSync, only write changed data if possible
	 * Either issued manually by {@link TileEntityBase#sendUpdate(boolean)} or by {@link net.minecraft.world.World#notifyBlockUpdate(BlockPos, IBlockState, IBlockState, int)}
	 */
	NETWORK_SYNC_PARTIAL(false);

	private final boolean isFullSync;

	public boolean isFullSync() { return this.isFullSync; }

	NbtSyncType(boolean isFullSync)
	{
		this.isFullSync = isFullSync;
	}
}
