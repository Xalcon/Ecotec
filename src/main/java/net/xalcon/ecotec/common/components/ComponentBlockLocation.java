package net.xalcon.ecotec.common.components;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.xalcon.ecotec.api.components.IBlockLocation;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.init.ModCaps;

public class ComponentBlockLocation implements IBlockLocation
{
	private TileEntity tile;

	@Override
	public void initialize(ICapabilityProvider provider)
	{
		if(provider instanceof TileEntity)
			this.tile = (TileEntity) provider;
	}

	@Override
	public void invalidate()
	{
		this.tile = null;
	}

	@Override
	public Capability<IBlockLocation> getCapability()
	{
		return ModCaps.getBlockLocationCap();
	}

	@Override
	public World getWorld()
	{
		return this.tile != null ? this.tile.getWorld() : null;
	}

	@Override
	public BlockPos getPos()
	{
		return this.tile != null ? this.tile.getPos() : null;
	}

	@Override
	public EnumFacing getBlockFacing()
	{
		// we must not call getBlockState() if the block is not loaded yet.
		// on world load, the tile entity gets initialized before isBlockLoaded() returns true
		// if we would call getBlockState() in that phase, we will cause an recursive infinite loop
		if(this.tile != null && this.tile.getWorld().isBlockLoaded(this.getPos()))
		{
			IBlockState state = this.tile.getWorld().getBlockState(this.getPos());
			if(state.getBlock() instanceof BlockMachineBase)
				return state.getValue(BlockMachineBase.FACING);
		}
		return null;
	}
}
