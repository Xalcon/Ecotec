package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.IEcotecHarvestable;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemDropoff;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.farmables.harvestable.TreeHarvestManager;
import net.xalcon.ecotec.common.fluids.FluidTankAdv;
import net.xalcon.ecotec.common.init.ModFluids;
import net.xalcon.ecotec.common.inventories.guiprovider.GuiProviderHarvester;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;
import net.xalcon.ecotec.common.util.IterativeAreaWalker;

import java.util.List;

public class TileEntityMachineHarvester extends TileEntityTickable
{
	private final ComponentItemDropoff itemDropoff;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;
	private FluidTank sludgeTank;
	private IterativeAreaWalker areaWalker;
	private TreeHarvestManager treeHarvestManager;

	public TileEntityMachineHarvester()
	{
		this.sludgeTank = new FluidTankAdv(this, ModFluids.FluidSludge, 0, Fluid.BUCKET_VOLUME * 4);

		this.itemDropoff = this.addComponent(new ComponentItemDropoff());
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderHarvester());
	}

	@Override
	protected boolean doWork()
	{
		if(this.areaWalker == null)
		{
			this.areaWalker = new IterativeAreaWalker(this.worldInteractive.getArea());
			this.treeHarvestManager = new TreeHarvestManager(this.getWorld());
		}

		if(!this.treeHarvestManager.isDone())
		{
			this.harvestTree();
			if(this.treeHarvestManager.isDone())
			{
				Ecotec.Log.info("Harvesting tree done");
			}
			return true;
		}

		BlockPos harvestPos = this.areaWalker.getNext();
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
						this.itemDropoff.dropItems(drops);
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
							this.itemDropoff.dropItems(drops);
						}
						break;
					}
					case ColumnKeepTop:
						break;
					case Tree:
						this.treeHarvestManager.reset(harvestPos);
						break;
				}
			}
		}
		return false;
	}

	private void harvestTree()
	{
		BlockPos harvestPos = this.treeHarvestManager.getNext();
		IBlockState harvestBlockState = this.getWorld().getBlockState(harvestPos);
		IEcotecHarvestable harvestable = EcotecRegistries.Harvestables.find(harvestBlockState.getBlock());
		if(harvestable == null) return;
		List<ItemStack> drops = harvestable.getDrops(this.getWorld(), harvestPos, harvestBlockState);
		harvestable.harvestBlock(this.getWorld(), harvestPos, harvestBlockState);
		this.itemDropoff.dropItems(drops);
	}

	public FluidTank getSludgeTank()
	{
		return this.sludgeTank;
	}
}
