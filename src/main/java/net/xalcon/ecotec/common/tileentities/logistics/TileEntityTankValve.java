package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.blocks.logistics.BlockFluidTank;
import net.xalcon.ecotec.common.components.ComponentFluidTank;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.multiblock.RectangularMultiblockHelper;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;
import net.xalcon.ecotec.common.multiblock.IEcotecMultiBlock;

import java.util.UUID;

public class TileEntityTankValve extends TileEntityTickable implements IEcotecMultiBlock
{
	private UUID multiblockIdentifier;
	private boolean isMaster;
	private boolean isFormed;
	private BlockPos masterPos;

	private ComponentFluidTank fluidTank;

	public TileEntityTankValve()
	{
		this.fluidTank = this.addComponent(new ComponentFluidTank(0));
	}

	@Override
	protected boolean doWork()
	{
		return false;
	}

	/*@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, @Nullable EnumFacing facing)
	{
		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return this.isMaster ? this.getWorld().getTileEntity(this.masterPos).getCapability(cap, facing);
		return super.getCapability(cap, facing);
	}*/

	@Override
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.readSyncNbt(nbt, type);
		this.isFormed = nbt.getBoolean("eco:isFormed");
		this.isMaster = nbt.getBoolean("eco:isMaster");
		if(this.isFormed && !this.isMaster)
			this.masterPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("eco:masterPos"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.writeSyncNbt(nbt, type);
		nbt.setBoolean("eco:isFormed", this.isFormed);
		nbt.setBoolean("eco:isMaster", this.isMaster);
		if(this.isFormed && !this.isMaster && this.masterPos != null)
			nbt.setTag("eco:masterPos", NBTUtil.createPosTag(this.masterPos));
	}

	@Override
	public boolean isFormed()
	{
		return this.isFormed;
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
		return this.multiblockIdentifier;
	}
}
