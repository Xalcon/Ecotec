package net.xalcon.minefactory.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.xalcon.minefactory.MinefactoryMod;

public abstract class ItemBase extends Item
{
	private String internalName;

	public ItemBase(String internalName)
	{
		this.internalName = internalName;
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels()
	{
		for (int i = 0; i < EnumRangeUpgradeType.values().length; i++)
		{
			MinefactoryMod.Proxy.registerItemRenderer(this, i, this.internalName, "inventory");
		}

	}
}
