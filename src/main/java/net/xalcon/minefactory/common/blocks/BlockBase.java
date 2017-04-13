package net.xalcon.minefactory.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
