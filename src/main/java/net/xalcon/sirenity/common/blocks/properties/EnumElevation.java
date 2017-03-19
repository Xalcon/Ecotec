package net.xalcon.sirenity.common.blocks.properties;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum EnumElevation implements IStringSerializable
{
	FLAT("flat"), ASCENDING("ascending"), DESCENDING("descending");

	private String name;

	EnumElevation(String name)
	{
		this.name = name;
	}

	@Nonnull
	public String getName()
	{
		return name;
	}
}
