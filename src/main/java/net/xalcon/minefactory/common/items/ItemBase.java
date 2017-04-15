package net.xalcon.minefactory.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.xalcon.minefactory.MinefactoryMod;
import net.xalcon.minefactory.client.IItemRenderRegister;

public abstract class ItemBase extends Item
{
	public ItemBase(String internalName)
	{
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels(IItemRenderRegister register)
	{
		register.registerItemRenderer(this, 0, this.getRegistryName(), "inventory");
	}
}
