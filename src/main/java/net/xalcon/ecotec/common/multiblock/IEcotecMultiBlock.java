package net.xalcon.ecotec.common.multiblock;

import java.util.UUID;

public interface IEcotecMultiBlock
{
	boolean isFormed();
	boolean tryFormMutliblock();
	UUID getMultiblockIdentifier();
}
