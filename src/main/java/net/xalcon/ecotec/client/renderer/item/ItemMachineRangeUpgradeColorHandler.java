package net.xalcon.ecotec.client.renderer.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.items.properties.EnumRangeUpgradeType;

public class ItemMachineRangeUpgradeColorHandler implements IItemColor
{
	public final static ItemMachineRangeUpgradeColorHandler Instance = new ItemMachineRangeUpgradeColorHandler();

	private ItemMachineRangeUpgradeColorHandler() { }

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		return tintIndex == 0 ? 0xFFFFFFFF : EnumRangeUpgradeType.values()[stack.getMetadata()].getColor().getRGB();
	}
}
