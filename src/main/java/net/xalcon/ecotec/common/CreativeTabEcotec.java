package net.xalcon.ecotec.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.init.ModBlocks;
import net.xalcon.ecotec.common.init.ModFluids;

public class CreativeTabEcotec extends CreativeTabs
{
	public static final CreativeTabEcotec Instance = new CreativeTabEcotec();

	public CreativeTabEcotec()
	{
		super(Ecotec.MODID + ".creativetab");
		this.setBackgroundImageName("item_search.png");
	}

	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(ModBlocks.MachineBreeder, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void displayAllRelevantItems(NonNullList<ItemStack> itemsToDisplay)
	{
		super.displayAllRelevantItems(itemsToDisplay);
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidMilk));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidMushroomSoup));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidMobEssence));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidSludge));
		itemsToDisplay.add(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, ModFluids.FluidSewage));
	}

	@Override
	public boolean hasSearchBar()
	{
		return true;
	}
}
