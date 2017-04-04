package net.xalcon.minefactory.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.xalcon.minefactory.MinefactoryMod;

public class BlockBase extends Block
{
	private String internalName;

	public BlockBase(Material materialIn, String internalName)
	{
		super(materialIn);
		this.internalName = internalName;
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels(ItemBlock itemBlock)
	{
		MinefactoryMod.Proxy.registerItemRenderer(itemBlock, 0, this.internalName, "inventory");
	}

	public String getInternalName()
	{
		return internalName;
	}
}