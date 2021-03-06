package net.xalcon.ecotec.common.tileentities.agriculture;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.xalcon.ecotec.EcotecRegistries;
import net.xalcon.ecotec.api.IEcotecPlantable;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveFrontal;
import net.xalcon.ecotec.common.components.ComponentWorldInteractiveSelf;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderPlanter;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;
import net.xalcon.ecotec.common.util.IterativeAreaWalker;

public class TileEntityMachinePlanter extends TileEntityTickable
{
	private final ComponentWorldInteractiveFrontal worldInteractive;
	private final ComponentEnergyStorage energyStorage;
	private final ComponentItemHandler inventory;
	private IterativeAreaWalker areaWalker;

	public TileEntityMachinePlanter()
	{
		this.worldInteractive = this.addComponent(new ComponentWorldInteractiveSelf(1, 0, 2));
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.inventory = this.addComponent(new ComponentItemHandler(9));
		this.addComponent(new GuiProviderPlanter());
	}

	@Override
	public void onLoad()
	{
		super.onLoad();
		this.areaWalker = new IterativeAreaWalker(this.worldInteractive.getArea());
	}

	@Override
	protected boolean doWork()
	{
		if(this.energyStorage.getEnergyStored() < 150) return false;

		BlockPos plantPos = this.areaWalker.getNext();
		for(int i = 0; i < this.inventory.getSlots(); i++)
		{
			ItemStack plantableStack = this.inventory.getStackInSlot(i);
			if(plantableStack.isEmpty()) continue;

			IEcotecPlantable plantable = EcotecRegistries.Plantables.find(plantableStack.getItem());
			if(plantable == null || !plantable.canBePlantedAt(this.getWorld(), plantPos, plantableStack)) continue;

			IBlockState plantBlockState = plantable.getPlantedBlock(this.getWorld(), plantPos, plantableStack);
			plantable.onPlanting(this.getWorld(), plantPos, plantableStack, plantBlockState);
			this.world.setBlockState(plantPos, plantBlockState);
			plantable.onPlanted(this.getWorld(), plantPos, plantableStack, plantBlockState);
			plantableStack.shrink(1);
			this.energyStorage.useEnergy(150);
			return true;
		}

		return false;
	}
}
