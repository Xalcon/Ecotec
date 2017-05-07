package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.IEntityRancherLogic;
import net.xalcon.ecotec.common.components.*;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderRancher;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityMachineRancher extends TileEntityTickable
{
	private final ComponentWorldInteractiveFrontal worldInteractive;
	private final ComponentEnergyStorage energyStorage;
	//private final ComponentItemHandler inventory;
	private ComponentFluidTank milkTank;
	private ComponentFluidTank mushroomSoupTank;

	public TileEntityMachineRancher()
	{
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal(1));
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		/*this.inventory = */this.addComponent(new ComponentItemHandler(9));
		this.addComponent(new GuiProviderRancher());

		this.milkTank = new ComponentFluidTank(ModFluids.FluidMilk, 0, Fluid.BUCKET_VOLUME * 4);
		this.mushroomSoupTank = new ComponentFluidTank(ModFluids.FluidMushroomSoup, 0, Fluid.BUCKET_VOLUME * 4);
		this.addComponent(new ComponentFluidMultiTank(this.milkTank, this.mushroomSoupTank));
		this.addComponent(new ComponentFluidItemInteraction(false, true));
	}

	@Override
	protected boolean doWork()
	{
		if(this.energyStorage.getEnergyStored() < 240) return false;

		AxisAlignedBB area = this.worldInteractive.getArea();
		for (EntityLiving entity : this.getWorld().getEntitiesWithinAABB(EntityLiving.class, area))
		{
			for (IEntityRancherLogic logic : EcotecRegistries.Ranchables.getEntries())
			{
				if (logic.ranchEntity(this, entity))
				{
					this.energyStorage.useEnergy(240);
					return true;
				}
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

	public FluidTank getMilkTank() { return this.milkTank; }
	public FluidTank getMushroomSoupTank() { return this.mushroomSoupTank; }
}
