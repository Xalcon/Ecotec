package net.xalcon.ecotec.common.tileentities.power;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class TileEntityGenerator extends TileEntityTickable
{
	private final ComponentEnergyStorage energyStorage;

	public TileEntityGenerator()
	{
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(1024, 1024, 1000000));
	}

	@Override
	public short getMaxIdleTime() { return 0; }

	@Override
	protected boolean doWork()
	{
		for(EnumFacing face : EnumFacing.values())
		{
			int amount = this.energyStorage.receiveEnergy(Integer.MAX_VALUE, true);
			if(amount == 0) return true;

			TileEntity te = this.getWorld().getTileEntity(this.getPos().offset(face));
			if(te == null) continue;

			IEnergyStorage adjacentStorage = te.getCapability(CapabilityEnergy.ENERGY, face.getOpposite());
			if(adjacentStorage == null || !adjacentStorage.canReceive()) continue;

			int accepted = adjacentStorage.receiveEnergy(amount, false);
			if(accepted == 0) continue;

			this.energyStorage.reduceEnergyStored(accepted);
		}

		this.energyStorage.reduceEnergyStored(4096);
		return true;
	}
}
