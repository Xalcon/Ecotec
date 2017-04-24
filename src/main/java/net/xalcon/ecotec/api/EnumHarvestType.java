package net.xalcon.ecotec.api;

public enum EnumHarvestType
{
	/**
	 * Simply break the block, no additional blocks will be searched
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
	 * Similar to {@link EnumHarvestType#Column} but keeps the bottom most block
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
	 * This block is part of a tree. Looks in all directions for other tree blocks.
	 * i.e.: Most trees in minecraft
	 */
	Tree,
	/**
	 * This block is a leaf block, part of a tree
	 */
	TreeLeaves
}
