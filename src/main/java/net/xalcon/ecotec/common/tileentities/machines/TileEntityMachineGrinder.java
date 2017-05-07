package net.xalcon.ecotec.common.tileentities.machines;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.api.components.IItemDropoff;
import net.xalcon.ecotec.api.components.IWorldInteractive;
import net.xalcon.ecotec.common.components.*;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderGrinder;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TileEntityMachineGrinder extends TileEntityTickable implements ITickable
{
	private static GameProfile profile = new GameProfile(UUID.fromString("B16B00B5-CAFE-BABE-1337-00FEEDC0DE00"), "ecotec:grinder");

	private FluidTank xpTank;
	private IItemDropoff itemDropoff;
	private IWorldInteractive worldInteractive;
	private ComponentEnergyStorage energyStorage;

	public TileEntityMachineGrinder()
	{
		this.xpTank = this.addComponent(new ComponentFluidTank(ModFluids.FluidMobEssence, 0, Fluid.BUCKET_VOLUME * 4));
		this.itemDropoff = this.addComponent(new ComponentItemDropoff());
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal());
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderGrinder());
		this.addComponent(new ComponentFluidItemInteraction(false, true));
	}

	@Override
	protected boolean doWork()
	{
		boolean workDone = false;
		AxisAlignedBB area = this.worldInteractive.getArea();

		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), profile);
		//player.getEntityAttribute(SharedMonsterAttributes.LUCK).setBaseValue(100);

		for (EntityMob entity : this.getWorld().getEntitiesWithinAABB(EntityMob.class, area))
		{
			if(entity.getHealth() <= 0) continue;
			if(this.energyStorage.getEnergyStored() < 200) break;
			entity.lastAttacker = player;
			entity.attackingPlayer = player;
			entity.recentlyHit = 60;
			entity.attackEntityFrom(new MachineDamageSource(player), Float.MAX_VALUE);
			workDone = true;
			this.energyStorage.useEnergy(200);
		}

		for (EntityXPOrb xp : this.getWorld().getEntitiesWithinAABB(EntityXPOrb.class, area))
		{
			this.xpTank.fill(new FluidStack(ModFluids.FluidMobEssence, (xp.xpValue * 10)), true);
			xp.setDead();
			workDone = true;
		}

		List<ItemStack> collectedItems = new ArrayList<>();
		for (EntityItem item : this.getWorld().getEntitiesWithinAABB(EntityItem.class, area))
		{
			// vanilla is missing a @Nullable :<
			//noinspection ConstantConditions
			if (item.getThrower() != null) continue;

			ItemStack itemStack = item.getEntityItem();
			if (!itemStack.isEmpty())
				collectedItems.add(itemStack);
			item.setDead();
			workDone = true;
		}

		if(collectedItems.size() > 0)
			this.itemDropoff.dropItems(collectedItems);

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

	public FluidTank getXpTank()
	{
		return this.xpTank;
	}

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

		@Nonnull
		@Override
		public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn)
		{
			return new TextComponentTranslation("death.attack." + this.damageType, entityLivingBaseIn.getDisplayName());
		}
	}
}
