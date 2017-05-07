package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.xalcon.ecotec.common.multiblock.IEcotecMultiBlock;
import net.xalcon.ecotec.common.multiblock.RectangularMultiblockHelper;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import java.util.UUID;

public class TileEntityTankSlave extends TileEntityBase implements IEcotecMultiBlock
{
	private boolean isFormed;
	private BlockPos masterPos;

	@Override
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.readSyncNbt(nbt, type);
		this.isFormed = nbt.getBoolean("eco:isFormed");
		if(this.isFormed)
			this.masterPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("eco:masterPos"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.writeSyncNbt(nbt, type);
		nbt.setBoolean("eco:isFormed", this.isFormed);
		if(this.isFormed && this.masterPos != null)
			nbt.setTag("eco:masterPos", NBTUtil.createPosTag(this.masterPos));
	}

	@Override
	public boolean isFormed()
	{
		return false;
	}

	@Override
	public boolean tryFormMutliblock()
	{
		if(this.world.isRemote) return false;
		RectangularMultiblockHelper.scanForMultiblock(this.world, this.pos);
		return false;
	}

	@Override
	public UUID getMultiblockIdentifier()
	{
		return null;
	}
}
