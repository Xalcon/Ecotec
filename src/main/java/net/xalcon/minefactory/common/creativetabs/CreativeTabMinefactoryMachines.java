package net.xalcon.minefactory.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.xalcon.minefactory.common.init.ModBlocks;

public class CreativeTabMinefactoryMachines extends CreativeTabs
{
	public static final CreativeTabMinefactoryMachines Instance = new CreativeTabMinefactoryMachines();

	public CreativeTabMinefactoryMachines()
	{
		super("machines");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.MachineBreeder, 1);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_)
	{
		super.displayAllRelevantItems(p_78018_1_);
		p_78018_1_.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModBlocks.FluidMilk.getFluid()));
		p_78018_1_.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModBlocks.FluidMushroomSoup.getFluid()));
		p_78018_1_.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModBlocks.FluidExperienceEssence.getFluid()));
		p_78018_1_.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModBlocks.FluidSludge.getFluid()));
		p_78018_1_.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModBlocks.FluidSewage.getFluid()));
	}
}
