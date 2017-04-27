package net.xalcon.ecotec.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.xalcon.ecotec.Ecotec;

public class EcotecNetwork
{
	private static SimpleNetworkWrapper network;

	public static void initNetwork()
	{
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Ecotec.MODID);

		// Packets send from server to client
		network.registerMessage(PacketUpdateClientTileEntityCustom.Handler.class, PacketUpdateClientTileEntityCustom.class, 0, Side.CLIENT);
	}

	public static SimpleNetworkWrapper getNetwork() { return network; }
}
