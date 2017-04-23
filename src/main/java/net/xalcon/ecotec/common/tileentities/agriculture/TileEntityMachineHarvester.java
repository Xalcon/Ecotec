package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.block.BlockPotato;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.IEcotecHarvestable;
import net.xalcon.ecotec.api.IEcotecPlantable;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.fluids.FluidTankAdv;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.tileentities.TileEntityMachineWorldInteractive;
import net.xalcon.ecotec.common.util.IterativeAreaWalker;

import java.util.List;

public class TileEntityMachineHarvester extends TileEntityMachineWorldInteractive implements ITickable
{
	private FluidTank sludgeTank;
	private IterativeAreaWalker areaWalker;

	public TileEntityMachineHarvester()
	{
		super(0);
		this.sludgeTank = new FluidTankAdv(this, ModFluids.FluidSludge, 0, Fluid.BUCKET_VOLUME * 4);
	}

	@Override
	public int getMaxIdleTicks()
	{
		return 5;
	}

	@Override
	public int getMaxProgressTicks()
	{
		return 1;
	}

	@Override
	protected boolean doWork()
	{
		if(this.areaWalker == null)
		{
			int radius = this.getWorkRadius();
			EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
			AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
			this.areaWalker = new IterativeAreaWalker(area);
		}

		BlockPos harvestPos = areaWalker.getNext();
		IBlockState harvestBlockState = this.getWorld().getBlockState(harvestPos);
		if(!harvestBlockState.getBlock().isAir(harvestBlockState, this.getWorld(), harvestPos))
		{
			IEcotecHarvestable harvestable = EcotecRegistries.Harvestables.find(harvestBlockState.getBlock());
			if(harvestable != null && harvestable.canBeHarvested(this.getWorld(), harvestPos, harvestBlockState))
			{
				switch (harvestable.getHarvestType())
				{
					case Normal:
					{
						List<ItemStack> drops = harvestable.getDrops(this.getWorld(), harvestPos, harvestBlockState);
						harvestable.harvestBlock(this.getWorld(), harvestPos, harvestBlockState);
						this.dropItems(drops);
						break;
					}
					case Column:
						break;
					case ColumnKeepBottom:
					{
						int i = 0;
						for (; i < 25; i++)
						{
							BlockPos abovePos = harvestPos.add(0, i, 0);
							IBlockState state = this.getWorld().getBlockState(abovePos);
							if (state.getBlock() != harvestBlockState.getBlock())
							{
								i--;
								break;
							}
						}
						if (i > 0)
						{
							BlockPos pos = harvestPos.add(0, i, 0);
							IBlockState state = this.getWorld().getBlockState(harvestPos.add(0, i, 0));
							List<ItemStack> drops = harvestable.getDrops(this.getWorld(), pos, state);
							harvestable.harvestBlock(this.getWorld(), pos, state);
							this.dropItems(drops);
						}
						break;
					}
					case ColumnKeepTop:
						break;
					case Tree:
						break;
					case TreeUpsideDown:
						break;
				}
			}
		}
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
