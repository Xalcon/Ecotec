package net.xalcon.ecotec.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.client.IItemRenderRegister;

public abstract class BlockBase extends Block
{
	private String internalName;

	public BlockBase(Material materialIn, String internalName)
	{
		super(materialIn);
		this.internalName = internalName;
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels(ItemBlock itemBlock, IItemRenderRegister register)
	{
		//noinspection ConstantConditions
		register.registerItemRenderer(itemBlock, 0, this.getRegistryName(), "inventory");
	}

	public String getInternalName()
	{
		return this.internalName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
