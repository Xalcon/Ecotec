package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.fluids.FluidMultiTank;
import net.xalcon.ecotec.common.fluids.FluidTankAdv;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;

import javax.annotation.Nullable;

public class TileEntityMachineRancher extends TileEntityMachineWorldInteractive
{
	private FluidTank milkTank = new FluidTankAdv(this, ModFluids.FluidMilk, 0, Fluid.BUCKET_VOLUME * 4);
	private FluidTank mushroomSoupTank = new FluidTankAdv(this, ModFluids.FluidMushroomSoup, 0, Fluid.BUCKET_VOLUME * 4);
	private FluidMultiTank multiTank = new FluidMultiTank(this.milkTank, this.mushroomSoupTank);

	@Override
	public String getUnlocalizedName()
	{
		return ModBlocks.MachineRancher.getUnlocalizedName();
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		int radius = this.getWorkRadius();
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 1, radius);
		for (EntityLiving entity : this.getWorld().getEntitiesWithinAABB(EntityLiving.class, area))
		{
			for (IEntityRancherLogic logic : EcotecRegistries.Ranchables.getEntries())
			{
				if (logic.ranchEntity(this, entity))
					return true;
			}
		}
		return false;
	}

	@Override
	protected ItemStackHandler createInventory()
	{
		return new ItemStackHandler(9);
	}

	@Override
	public void readSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.readSyncNbt(compound, type);
		this.milkTank.readFromNBT(compound.getCompoundTag("milkTank"));
		this.mushroomSoupTank.readFromNBT(compound.getCompoundTag("mushroomSoupTank"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.writeSyncNbt(compound, type);
		compound.setTag("milkTank", this.milkTank.writeToNBT(new NBTTagCompound()));
		compound.setTag("mushroomSoupTank", this.mushroomSoupTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
				? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.multiTank)
				: super.getCapability(capability, facing);
	}


	public FluidTank getMilkTank() { return this.milkTank; }


	public FluidTank getMushroomSoupTank() { return this.mushroomSoupTank; }
}
