package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.fluids.FluidMultiTank;
import net.xalcon.ecotec.common.fluids.FluidTankAdv;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderDeepStorageUnit;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderRancher;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import javax.annotation.Nullable;

public class TileEntityMachineRancher extends TileEntityTickable
{
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;
	//private final ComponentItemHandler inventory;
	private FluidTank milkTank = new FluidTankAdv(this, ModFluids.FluidMilk, 0, Fluid.BUCKET_VOLUME * 4);
	private FluidTank mushroomSoupTank = new FluidTankAdv(this, ModFluids.FluidMushroomSoup, 0, Fluid.BUCKET_VOLUME * 4);
	private FluidMultiTank multiTank = new FluidMultiTank(this.milkTank, this.mushroomSoupTank);

	public TileEntityMachineRancher()
	{
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		/*this.inventory = */this.addComponent(new ComponentItemHandler(9));
		this.addComponent(new GuiProviderRancher());
	}

	@Override
	protected boolean doWork()
	{
		AxisAlignedBB area = this.worldInteractive.getArea();
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
