package net.xalcon.minefactory.common.tileentities.machines;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineBase;
import net.xalcon.minefactory.common.tileentities.machines.rancherlogic.EntityRancherSheepLogic;
import net.xalcon.minefactory.common.tileentities.machines.rancherlogic.IEntityRancherLogic;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TileEntityMachineRancher extends TileEntityMachineBase implements ITickable
{
	public TileEntityMachineRancher()
	{
		super(9);
	}

	private int radius = 2;

	private static List<IEntityRancherLogic> rancherLogicList = new ArrayList<>();

	static
	{
		rancherLogicList.add(new EntityRancherSheepLogic());
	}

	@Nonnull
	@Override
	public String getName()
	{
		return "machine_rancher";
	}

	@Override
	public void update()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(EntityAnimal animal : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			for(IEntityRancherLogic logic : rancherLogicList)
			{
				if(logic.ranchEntity(this, animal)) return;
			}
		}
	}

	public AxisAlignedBB getWorkBounds()
	{
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		return new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
	}
}
