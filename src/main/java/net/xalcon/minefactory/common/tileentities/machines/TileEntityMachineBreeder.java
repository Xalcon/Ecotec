package net.xalcon.minefactory.common.tileentities.machines;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.xalcon.minefactory.common.blocks.BlockMachineBase;
import net.xalcon.minefactory.common.tileentities.TileEntityMachine;
import net.xalcon.minefactory.common.tileentities.TileEntityMachineWorldInteractive;

import java.util.UUID;

public class TileEntityMachineBreeder extends TileEntityMachineWorldInteractive implements ITickable
{
	public TileEntityMachineBreeder()
	{
		super(9);
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
		int radius = this.getWorkRadius();
		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), new GameProfile(new UUID(0x12345678, 0x11223344), "minefactory:breeder"));
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			if(!entity.isInLove() && entity.getGrowingAge() == 0)
			{
				for(int i = 0; i < this.inventory.getSlots(); i++)
				{
					ItemStack stack = this.inventory.getStackInSlot(i);
					if(entity.isBreedingItem(stack))
					{
						entity.setInLove(player); // TODO: Add breeding cap
						stack.shrink(1);
						this.markDirty();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String getUnlocalizedName()
	{
		return "machine_breeder";
	}
}
