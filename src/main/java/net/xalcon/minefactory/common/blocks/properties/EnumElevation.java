package net.xalcon.minefactory.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum EnumElevation implements IStringSerializable
{
	FLAT("flat"), ASCENDING("ascending"), DESCENDING("descending");

	private String name;

	EnumElevation(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
