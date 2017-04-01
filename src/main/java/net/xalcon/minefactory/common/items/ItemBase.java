package net.xalcon.minefactory.common.items;

import net.minecraft.item.Item;
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
		MinefactoryMod.Proxy.registerItemRenderer(this, 0, this.internalName, "inventory");

	}
}
