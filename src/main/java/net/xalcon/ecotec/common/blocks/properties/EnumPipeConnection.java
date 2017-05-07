package net.xalcon.ecotec.common.blocks.properties;

public enum EnumPipeConnection
{
	DISCONNECTED("disonnected"),
	CONNECTED("connected"),
	CONNECTED_TILE_INOUT("connected_tile_inout"),
	CONNECTED_TILE_IN("connected_tile_in"),
	CONNECTED_TILE_OUT("connected_tile_out");

	private String name;

	EnumPipeConnection(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}
}
