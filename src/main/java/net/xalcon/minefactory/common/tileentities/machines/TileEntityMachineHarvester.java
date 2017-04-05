package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;
import net.xalcon.minefactory.common.util.BlockMath;

public class TileEntityMachineHarvester extends TileEntityMachineBase implements ITickable
{
	private int checkIndex;

	public TileEntityMachineHarvester()
	{
		super(0);
	}

	@Override
	public void update()
	{
		/*IBlockState[] blocks = {
				Blocks.STONE.getDefaultState(),
				Blocks.IRON_BLOCK.getDefaultState(),
				Blocks.COBBLESTONE.getDefaultState(),

				Blocks.LAPIS_BLOCK.getDefaultState(),
				Blocks.DIAMOND_BLOCK.getDefaultState(),
				Blocks.REDSTONE_BLOCK.getDefaultState(),

				Blocks.LOG.getDefaultState(),
				Blocks.GLASS.getDefaultState(),
				Blocks.BEDROCK.getDefaultState()
		};

		int radius = this.getWorkRadius();
		IBlockState blockState = this.getWorld().getBlockState(this.getPos());
		EnumFacing blockFacing = blockState.getValue(BlockMachineBase.FACING);

		int diameter = radius * 2 + 1;
		int totalSlots = diameter * diameter;
		int x = (checkIndex % diameter) - radius;
		int z = (checkIndex / diameter) - diameter;

		BlockMath.RelativePosition relPos = BlockMath.rotatePosition(x, z, blockFacing);

		BlockPos pos = new BlockPos(this.getPos().getX() + relPos.x, this.getPos().getY(), this.getPos().getZ() + relPos.z);
		if(world.getBlockState(pos).getMaterial().isReplaceable())
			world.setBlockState(pos, blocks[(checkIndex % blocks.length)]);

		checkIndex = (checkIndex + 1) % totalSlots;*/
	}

	@Override
	public String getName()
	{
		return "inventory.machine_harvester";
	}
}
