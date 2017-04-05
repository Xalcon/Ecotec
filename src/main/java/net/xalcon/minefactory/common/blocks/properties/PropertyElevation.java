package net.xalcon.minefactory.common.blocks.properties;

import net.minecraft.block.properties.PropertyEnum;

import java.util.Arrays;
import java.util.Collection;

public class PropertyElevation extends PropertyEnum<EnumElevation>
{
	private PropertyElevation(String name, Collection<EnumElevation> values)
	{
		super(name, EnumElevation.class, values);
	}

	public static PropertyElevation create(String name)
	{
		return create(name, Arrays.asList(EnumElevation.values()));
	}

	public static PropertyElevation create(String name, Collection<EnumElevation> values)
	{
		return new PropertyElevation(name, values);
	}
}
