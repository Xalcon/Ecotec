package net.xalcon.ecotec.common.blocks.properties;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;

public enum EnumTankBlockType implements IStringSerializable
{
	WALL("wall", BlockRenderLayer.SOLID, true),
	WINDOW("window", BlockRenderLayer.CUTOUT, false),
	VALVE("valve", BlockRenderLayer.SOLID, false);

	private String name;
	private BlockRenderLayer layer;
	private boolean isOpaque;
	private boolean isEdgeBlock;

	EnumTankBlockType(String name, BlockRenderLayer layer, boolean isEdgeBlock)
	{
		this(name, layer, isEdgeBlock, layer == BlockRenderLayer.SOLID);
	}

	EnumTankBlockType(String name, BlockRenderLayer layer, boolean isEdgeBlock, boolean isOpaque)
	{
		this.name = name;
		this.layer = layer;
		this.isOpaque = isOpaque;
		this.isEdgeBlock = isEdgeBlock;
	}

	public BlockRenderLayer getLayer()
	{
		return this.layer;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public boolean isOpaque()
	{
		return this.isOpaque;
	}

	public int getMeta()
	{
		return this.ordinal();
	}

	public static EnumTankBlockType fromMeta(int meta)
	{
		if(meta < 0 || meta >= values().length)
			return WALL;
		return values()[meta];
	}

	public boolean isEdgeBlock()
	{
		return this.isEdgeBlock;
	}
}
