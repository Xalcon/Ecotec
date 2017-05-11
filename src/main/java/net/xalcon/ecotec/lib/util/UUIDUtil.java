package net.xalcon.ecotec.lib.util;

import java.util.Random;
import java.util.UUID;

public class UUIDUtil
{
	public static UUID generateUUID(String input)
	{
		long seed = 0;
		for(Character c : input.toCharArray())
			seed = 31L * seed + c;
		Random random = new Random(seed);
		byte[] uuidBytes = new byte[16];
		random.nextBytes(uuidBytes);
		return UUID.nameUUIDFromBytes(uuidBytes);
	}
}
