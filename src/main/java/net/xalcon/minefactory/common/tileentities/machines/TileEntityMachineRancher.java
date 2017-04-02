package net.xalcon.minefactory.common.tileentities.machines;

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
import net.xalcon.minefactory.common.tileentities.machines.rancherlogic.EntityRancherSheepLogic;
import net.xalcon.minefactory.common.tileentities.machines.rancherlogic.IEntityRancherLogic;

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

	private int radius = 2;

	private static List<IEntityRancherLogic> rancherLogicList = new ArrayList<>();

	private FluidTank milkTank = new FluidTankAdv(this, ModBlocks.FluidMilk.getFluid(), 0, Fluid.BUCKET_VOLUME * 4);
	private FluidTank mushroomSoupTank = new FluidTankAdv(this, ModBlocks.FluidMushroomSoup.getFluid(), 0, Fluid.BUCKET_VOLUME * 4);
	private FluidMultiTank multiTank = new FluidMultiTank(milkTank, mushroomSoupTank);

	static
	{
		rancherLogicList.add(new EntityRancherSheepLogic());
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "machine_rancher";
	}

	@Override
	public void update()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(EntityAnimal animal : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			for(IEntityRancherLogic logic : rancherLogicList)
			{
				if(logic.ranchEntity(this, animal)) return;
			}
		}
	}

	@Override
	@Nonnull
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(super.getUpdateTag());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.milkTank.readFromNBT(compound.getCompoundTag("milkTank"));
		this.mushroomSoupTank.readFromNBT(compound.getCompoundTag("mushroomSoupTank"));
	}

	@Nonnull
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

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.multiTank);
		return super.getCapability(capability, facing);
	}

	public FluidTank getMilkTank() { return this.milkTank; }
	public FluidTank getMushroomSoupTank() { return this.mushroomSoupTank; }

	public AxisAlignedBB getWorkBounds()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		return new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
	}
}
