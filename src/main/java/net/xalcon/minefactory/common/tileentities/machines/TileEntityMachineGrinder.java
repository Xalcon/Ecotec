package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.init.ModBlocks;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityMachineGrinder extends TileEntityMachineBase implements ITickable
{
	class MachineDamageSource extends DamageSource
	{

		public MachineDamageSource()
		{
			super("minefactory.grinder");
			this.setDamageBypassesArmor();
			this.setDamageIsAbsolute();
		}

		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			return new TextComponentTranslation("death.attack." + this.damageType, entityLivingBaseIn.getDisplayName());
		}
	}

	private FluidTank xpTank;
	private int radius = 2;

	public TileEntityMachineGrinder()
	{
		super(9);
		this.xpTank = new FluidTank(ModBlocks.FluidExperienceEssence.getFluid(), 0, Fluid.BUCKET_VOLUME * 4);
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "container.machine_grinder";
	}

	@Override
	public void update()
	{
		if(this.getWorld().isRemote) return;
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(EntityLivingBase entity : this.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, area))
		{
			if(entity.isChild() || entity instanceof EntityPlayer) continue;
			entity.attackEntityFrom(new MachineDamageSource(), Float.MAX_VALUE);
		}

		for(EntityXPOrb xp : this.getWorld().getEntitiesWithinAABB(EntityXPOrb.class, area))
		{
			this.xpTank.fill(new FluidStack(ModBlocks.FluidExperienceEssence.getFluid(), xp.xpValue), true);
			xp.setDead();
		}

		for(EntityItem item : this.getWorld().getEntitiesWithinAABB(EntityItem.class, area))
		{
			// not sure what intellij is talking about but getThrower() can be null!
			if(item.getThrower() != null) continue;

			ItemStack itemStack = item.getEntityItem();
			if(!itemStack.isEmpty())
			{
				// TODO: handle overflow
				this.insertItemStack(itemStack);
			}
			item.setDead();
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
		this.xpTank.readFromNBT(compound.getCompoundTag("xpTank"));
	}

	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		compound = super.writeToNBT(compound);
		compound.setTag("xpTank", this.xpTank.writeToNBT(new NBTTagCompound()));
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
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.xpTank);
		return super.getCapability(capability, facing);
	}

	public FluidTank getXpTank()
	{
		return xpTank;
	}
}
