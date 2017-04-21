package net.xalcon.minefactory.api;

public enum HarvestType
{
	/**
	 * Simply break the block
	 * i.e.: Wheat, Potatos, Carrots, Pumpkin, Melon
	 */
	Normal,
	/**
	 * Plant grows in a column. Breaks upmost blocks first.
	 * No horizontal search. Only similar block type.
	 * i.e.: Hemp from Immersive Engineering
	 */
	Column,
	/**
	 * Similar to {@link HarvestType#Column} but keeps the bottom most block
	 * i.e.: Sugarcane, Cactus
	 */
	ColumnKeepBottom,
	/**
	 * Plant grows upside down in a column. Breaks bottom most blocks first and keeps the up most block
	 * No horizontal search. Only similar block type.
	 * i.e.: Vines
	 */
	ColumnKeepTop,
	/**
	 * This block is part of a tree. Look for the upmost block and break everything related, starting with leaves.
	 * i.e.: Most trees in minecraft
	 */
	Tree,
	/**
	 * Similar to {@link HarvestType#Tree} but instead of looking upwards, we go downwards.
	 * i.e.: Natura
	 */
	TreeUpsideDown
}
