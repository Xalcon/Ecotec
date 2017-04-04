package net.xalcon.minefactory.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMachineRangeUpgrade extends ItemBase
{
	public ItemMachineRangeUpgrade()
	{
		super("machine_range_upgrade");
		this.setHasSubtypes(true);
	}

	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + EnumRangeUpgradeType.getFromMeta(stack.getMetadata()).getUnlocalizedName();
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		for (int i = 0; i < EnumRangeUpgradeType.values().length; ++i)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		EnumRangeUpgradeType upgrade = EnumRangeUpgradeType.getFromMeta(stack.getMetadata());
		if(upgrade.getRange() > 0)
			tooltip.add("Increases radius by " + upgrade.getRange());
		else
			tooltip.add("Decreases radius by " + upgrade.getRange() * -1);
	}
}
