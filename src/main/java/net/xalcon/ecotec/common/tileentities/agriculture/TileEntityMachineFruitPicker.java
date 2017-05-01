package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemDropoff;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;
import net.xalcon.ecotec.common.util.IterativeAreaWalker;

import java.util.List;

public class TileEntityMachineFruitPicker extends TileEntityTickable
{
	private final ComponentItemDropoff itemDropoff;
	private final ComponentWorldInteractiveFrontal worldInteractive;
	//private final ComponentEnergyStorage energyStorage;
	private IterativeAreaWalker areaWalker;

	public TileEntityMachineFruitPicker()
	{
		this.itemDropoff = this.addComponent(ModCaps.getItemDropoffCap(), new ComponentItemDropoff());
		this.worldInteractive = this.addComponent(ModCaps.getWorldInteractiveCap(), new ComponentWorldInteractiveFrontal(1));
		/*this.energyStorage = */this.addComponent(CapabilityEnergy.ENERGY, new ComponentEnergyStorage(512, 0, 16000));
	}

	@Override
	protected boolean doWork()
	{
		if(this.areaWalker == null)
		{
			this.areaWalker = new IterativeAreaWalker(this.worldInteractive.getArea());
		}

		BlockPos cropPos = this.areaWalker.getNext();
		IBlockState state = this.getWorld().getBlockState(cropPos);
		return this.harvestCrop(cropPos, state);
	}

	private boolean harvestCrop(BlockPos cropPos, IBlockState state)
	{
		if (state.getBlock() instanceof BlockCrops)
		{
			BlockCrops crop = (BlockCrops) state.getBlock();
			if (crop.isMaxAge(state))
			{
				List<ItemStack> drops = crop.getDrops(this.getWorld(), cropPos, state, 0);

				Item seedItem = crop.getItemDropped(crop.withAge(0), this.world.rand, 0);
				for (ItemStack drop : drops)
				{
					if (!(drop.getItem() == seedItem) || crop instanceof BlockCarrot || crop instanceof BlockPotato)
					{
						drops.remove(drop);
						break;
					}
				}

				this.itemDropoff.dropItems(drops);
				return this.world.setBlockState(cropPos, crop.withAge(0));
			}
		}
		return false;
	}
}
