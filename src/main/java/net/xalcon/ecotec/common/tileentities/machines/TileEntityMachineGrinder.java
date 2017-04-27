package net.xalcon.ecotec.common.tileentities.machines;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
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
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.fluids.FluidTankAdv;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;

import javax.annotation.Nullable;
import java.util.UUID;

public class TileEntityMachineGrinder extends TileEntityMachineWorldInteractive implements ITickable
{
	private static GameProfile profile = new GameProfile(UUID.fromString("B16B00B5-CAFE-BABE-1337-00FEEDC0DE00"), "ecotec:grinder");

	class MachineDamageSource extends DamageSource
	{
		private final Entity entity;

		public MachineDamageSource(Entity source)
		{
			super("ecotec.grinder");
			this.setDamageBypassesArmor();
			this.setDamageIsAbsolute();
			this.entity = source;
		}

		@Override
		public Entity getEntity()
		{
			return this.entity;
		}

		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			return new TextComponentTranslation("death.attack." + this.damageType, entityLivingBaseIn.getDisplayName());
		}
	}

	private FluidTank xpTank;

	public TileEntityMachineGrinder()
	{
		super(9);
		this.xpTank = new FluidTankAdv(this, ModFluids.FluidExperienceEssence, 0, Fluid.BUCKET_VOLUME * 4);
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_grinder";
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
		boolean workDone = false;
		int radius = this.getWorkRadius();
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);

		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), profile);
		//player.getEntityAttribute(SharedMonsterAttributes.LUCK).setBaseValue(100);

		for (EntityMob entity : this.getWorld().getEntitiesWithinAABB(EntityMob.class, area))
		{
			entity.lastAttacker = player;
			entity.attackingPlayer = player;
			entity.recentlyHit = 60;
			entity.attackEntityFrom(new MachineDamageSource(entity), Float.MAX_VALUE);
			workDone = true;
			//break;
		}

		for (EntityXPOrb xp : this.getWorld().getEntitiesWithinAABB(EntityXPOrb.class, area))
		{
			this.xpTank.fill(new FluidStack(ModFluids.FluidExperienceEssence, (int) (xp.xpValue * (200f / 3f))), true);
			xp.setDead();
			workDone = true;
		}

		for (EntityItem item : this.getWorld().getEntitiesWithinAABB(EntityItem.class, area))
		{
			// not sure what intellij is talking about but getThrower() can be null!
			//noinspection ConstantConditions
			if (item.getThrower() != null) continue;

			ItemStack itemStack = item.getEntityItem();
			if (!itemStack.isEmpty())
			{
				this.dropItem(itemStack);
			}
			item.setDead();
			workDone = true;
		}
		return workDone;
	}

	@Override
	public void readSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.readSyncNbt(compound, type);
		this.xpTank.readFromNBT(compound.getCompoundTag("xpTank"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound compound, NbtSyncType type)
	{
		super.writeSyncNbt(compound, type);
		compound.setTag("xpTank", this.xpTank.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.xpTank);
		return super.getCapability(capability, facing);
	}

	public FluidTank getXpTank()
	{
		return this.xpTank;
	}
}
