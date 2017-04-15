package net.xalcon.minefactory.client.renderer.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.xalcon.minefactory.common.items.properties.EnumRangeUpgradeType;

public class ItemMachineRangeUpgradeColorHandler implements IItemColor
{
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return tintIndex == 0 ? 0xFFFFFFFF : EnumRangeUpgradeType.values()[stack.getMetadata()].getColor().getRGB();
	}
}
