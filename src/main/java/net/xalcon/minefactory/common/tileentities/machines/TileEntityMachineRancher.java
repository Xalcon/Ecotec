package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.fluids.FluidMultiTank;
import net.xalcon.minefactory.common.fluids.FluidTankAdv;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;
import net.xalcon.minefactory.common.tileentities.machines.rancherlogic.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TileEntityMachineRancher extends TileEntityMachineBase implements ITickable
{
	public TileEntityMachineRancher()
	{
		super(9);
	}

	private static List<IEntityRancherLogic> rancherLogicList = new ArrayList<>();

	private FluidTank milkTank = new FluidTankAdv(this, ModBlocks.FluidMilk.getFluid(), 0, Fluid.BUCKET_VOLUME * 4);
	private FluidTank mushroomSoupTank = new FluidTankAdv(this, ModBlocks.FluidMushroomSoup.getFluid(), 0, Fluid.BUCKET_VOLUME * 4);
	private FluidMultiTank multiTank = new FluidMultiTank(milkTank, mushroomSoupTank);

	static
	{
		rancherLogicList.add(new EntityRancherSheepLogic());
		rancherLogicList.add(new EntityRancherCowLogic());
		rancherLogicList.add(new EntityRancherMooshroomLogic());
		rancherLogicList.add(new EntityRancherSquidLogic());
	}

	@Override
	public String getName()
	{
		return "machine_rancher";
	}

	@Override
	public void update()
	{
		if(this.getWorld().isRemote) return;
		int radius = this.getWorkRadius();
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 1, radius);
		for(EntityLiving entity : this.getWorld().getEntitiesWithinAABB(EntityLiving.class, area))
		{
			for(IEntityRancherLogic logic : rancherLogicList)
			{
				if(logic.ranchEntity(this, entity)) return;
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.milkTank.readFromNBT(compound.getCompoundTag("milkTank"));
		this.mushroomSoupTank.readFromNBT(compound.getCompoundTag("mushroomSoupTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);
		compound.setTag("milkTank", this.milkTank.writeToNBT(new NBTTagCompound()));
		compound.setTag("mushroomSoupTank", this.mushroomSoupTank.writeToNBT(new NBTTagCompound()));
		return compound;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.multiTank);
		return super.getCapability(capability, facing);
	}

	public FluidTank getMilkTank() { return this.milkTank; }
	public FluidTank getMushroomSoupTank() { return this.mushroomSoupTank; }
}
