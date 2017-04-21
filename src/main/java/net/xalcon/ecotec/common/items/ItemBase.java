package net.xalcon.ecotec.common.items;

import net.minecraft.item.Item;
import net.xalcon.ecotec.client.IItemRenderRegister;

public abstract class ItemBase extends Item
{
	public ItemBase(String internalName)
	{
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels(IItemRenderRegister register)
	{
		//noinspection ConstantConditions
		register.registerItemRenderer(this, 0, this.getRegistryName(), "inventory");
	}
}
