package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.minefactory.common.fluids.FluidTankAdv;
import net.xalcon.minefactory.common.init.ModFluids;
import net.xalcon.minefactory.common.tileentities.TileEntityMachine;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineWorldInteractive;

public class TileEntityMachineHarvester extends TileEntityMachineWorldInteractive implements ITickable
{
	private int checkIndex;
	private FluidTank sludgeTank;

	public TileEntityMachineHarvester()
	{
		super(0);
		this.sludgeTank = new FluidTankAdv(this, ModFluids.FluidSludge, 0, Fluid.BUCKET_VOLUME * 4);
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 100;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		/*IBlockState[] BlockList = {
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
			world.setBlockState(pos, BlockList[(checkIndex % BlockList.length)]);

		checkIndex = (checkIndex + 1) % totalSlots;*/
		return false;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_harvester";
	}

	public FluidTank getSludgeTank()
	{
		return this.sludgeTank;
	}
}
