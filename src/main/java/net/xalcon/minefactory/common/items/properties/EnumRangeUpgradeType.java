package net.xalcon.minefactory.common.items.properties;

import java.awt.*;

public enum EnumRangeUpgradeType
{
	COBBLE(-1, "cobblestone", Color.GRAY),
	IRON(1, "iron", Color.LIGHT_GRAY),
	LAPIS(2, "lapislazuli", Color.BLUE),
	GOLD(3, "gold", Color.YELLOW),
	QUARTZ(4, "quartz", Color.WHITE),
	DIAMOND(5, "diamond", Color.CYAN),
	EMERALD(6, "emerald", Color.GREEN);

	private int range;
	private String unlocalizedName;
	private Color color;

	EnumRangeUpgradeType(int range, String unlocalizedName, Color color)
	{
		this.range = range;
		this.unlocalizedName = unlocalizedName;
		this.color = color;
	}

	public int getRange()
	{
		return range;
	}

	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}

	public Color getColor()
	{
		return color;
	}

	public static EnumRangeUpgradeType getFromMeta(int meta)
	{
		if (meta > EnumRangeUpgradeType.values().length)
			meta = 0;
		return EnumRangeUpgradeType.values()[meta];
	}
}
