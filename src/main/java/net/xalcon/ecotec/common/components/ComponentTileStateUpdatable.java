package net.xalcon.ecotec.common.components;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.ecotec.api.components.IStateUpdatable;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

public class ComponentTileStateUpdatable implements IStateUpdatable
{
	private Runnable updateFunc;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		if(provider instanceof TileEntityTickable)
			this.updateFunc = ((TileEntityTickable) provider)::markForUpdate;
		else if(provider instanceof TileEntityBase)
			this.updateFunc = () -> ((TileEntityBase) provider).sendUpdate(false);
		else if(provider instanceof TileEntity)
			this.updateFunc = () ->
			{
				TileEntity te = (TileEntity) provider;
				IBlockState state = te.getWorld().getBlockState(te.getPos());
				te.getWorld().notifyBlockUpdate(te.getPos(), state, state, 0);
			};
		else
			this.updateFunc = null;
	}

	@Override
	public void invalidate()
	{
		this.updateFunc = null;
	}

	@Override
	public void markDirty()
	{
		if(this.updateFunc != null)
			this.updateFunc.run();
	}
}
