package net.xalcon.ecotec.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModFluids;

public class CreativeTabEcotecMachines extends CreativeTabs
{
	public static final CreativeTabEcotecMachines Instance = new CreativeTabEcotecMachines();

	public CreativeTabEcotecMachines()
	{
		super("machines");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.MachineBreeder, 1);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> itemsToDisplay)
	{
		super.displayAllRelevantItems(itemsToDisplay);
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidMilk));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidMushroomSoup));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidExperienceEssence));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidSludge));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidSewage));
	}
}
