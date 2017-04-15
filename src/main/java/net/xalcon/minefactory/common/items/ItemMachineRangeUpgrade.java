package net.xalcon.minefactory.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.minefactory.client.IItemRenderRegister;
import net.xalcon.minefactory.common.items.properties.EnumRangeUpgradeType;

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

	@Override
	public void registerItemModels(IItemRenderRegister register)
	{
		for (int i = 0; i < EnumRangeUpgradeType.values().length; i++)
		{
			//noinspection ConstantConditions
			register.registerItemRenderer(this, i, this.getRegistryName(), "inventory");
		}
	}

	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		for (int i = 0; i < EnumRangeUpgradeType.values().length; i++)
		{
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		EnumRangeUpgradeType upgrade = EnumRangeUpgradeType.getFromMeta(stack.getMetadata());
		if (upgrade.getRange() > 0)
			tooltip.add(I18n.format("tooltip.machine_range_upgrade.range_increase", upgrade.getRange()));
		else
			tooltip.add(I18n.format("tooltip.machine_range_upgrade.range_decrease", upgrade.getRange() * -1));
	}
}
