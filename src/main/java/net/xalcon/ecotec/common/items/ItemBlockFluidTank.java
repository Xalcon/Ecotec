package net.xalcon.ecotec.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.xalcon.ecotec.common.blocks.properties.EnumTankBlockType;

public class ItemBlockFluidTank extends ItemBlock
{
	public ItemBlockFluidTank(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.block.getUnlocalizedName() + "." + EnumTankBlockType.fromMeta(stack.getMetadata()).getName();
	}
}
