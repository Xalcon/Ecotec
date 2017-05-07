package net.xalcon.ecotec.common.tileentities.machines;

import javafx.scene.chart.Axis;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionAbsorption;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.common.components.*;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderSludgeBoiler;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import java.util.Collections;

public class TileEntityMachineSludgeBoiler extends TileEntityTickable
{
	private final static ItemStack[] LIST =
	{
		new ItemStack(Blocks.DIRT, 1), new ItemStack(Blocks.DIRT, 1), new ItemStack(Blocks.DIRT, 1),
		new ItemStack(Blocks.DIRT, 1, 1),
		new ItemStack(Blocks.DIRT, 1, 2),
		new ItemStack(Blocks.SOUL_SAND, 1),
		new ItemStack(Blocks.SAND, 1), new ItemStack(Blocks.SAND, 1), new ItemStack(Blocks.SAND, 1),
		new ItemStack(Blocks.NETHERRACK, 1),
		new ItemStack(Blocks.GRAVEL, 1), new ItemStack(Blocks.GRAVEL, 1),
		new ItemStack(Blocks.MYCELIUM, 1),
		new ItemStack(Items.CLAY_BALL, 1), new ItemStack(Items.CLAY_BALL, 1),
		new ItemStack(Blocks.CLAY, 1),
	};

	private final ComponentItemDropoff itemDropoff;
	private final ComponentEnergyStorage energyStorage;
	private final ComponentWorldInteractiveSelf worldInteractive;
	private final ComponentFluidTank fluidTank;

	public TileEntityMachineSludgeBoiler()
	{
		this.itemDropoff = this.addComponent(new ComponentItemDropoff());
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveSelf(3, 2, 0));
		this.fluidTank = this.addComponent(new ComponentFluidTank(ModFluids.FluidSludge, 0, 8000));
		this.addComponent(new GuiProviderSludgeBoiler());
		this.addComponent(new ComponentFluidItemInteraction(true, false));
	}

	@Override
	protected boolean doWork()
	{
		if(this.energyStorage.getEnergyStored() < 100) return false;
		FluidStack stack = this.fluidTank.getFluid();
		if(stack == null || stack.getFluid() != ModFluids.FluidSludge || this.fluidTank.getFluidAmount() < 100) return false;
		this.energyStorage.useEnergy(100);
		this.fluidTank.useFluid(100);
		this.itemDropoff.dropItems(Collections.singleton(LIST[this.world.rand.nextInt(LIST.length)]));

		for(EntityLivingBase entity : this.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, this.worldInteractive.getArea()))
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 20 * 20, 2));
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20));
			entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * 20));
			entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20 * 20));
			entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * 20));
		}

		return false;
	}

	public FluidTank getFluidTank() { return this.fluidTank; }
}
