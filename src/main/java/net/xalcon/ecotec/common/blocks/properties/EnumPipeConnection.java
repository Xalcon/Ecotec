package net.xalcon.ecotec.common.blocks.properties;

public enum EnumPipeConnection
{
	DISCONNECTED(0, -1, "disconnected"),
	CONNECTED(1, 0, "normal"),
	CONNECTED_TILE_INOUT(2, 1, "inout"),
	CONNECTED_TILE_IN(3, 2, "in"),
	CONNECTED_TILE_OUT(4, 3, "out");

	private String name;
	private int index;

	EnumPipeConnection(int id, int mId, String name)
	{
		this.index = id;
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	public int getIndex() { return this.index; }

	public static EnumPipeConnection fromIndex(int index)
	{
		return EnumPipeConnection.values()[index];
	}
}
