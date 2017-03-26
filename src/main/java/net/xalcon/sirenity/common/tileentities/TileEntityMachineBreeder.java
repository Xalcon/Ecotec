package net.xalcon.sirenity.common.tileentities;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.xalcon.sirenity.common.blocks.BlockMachineBase;

import javax.annotation.Nonnull;
import java.util.UUID;

public class TileEntityMachineBreeder extends TileEntityMachineBase implements ITickable
{
	private int radius = 2;

	public TileEntityMachineBreeder()
	{
		super(9);
	}

	@Override
	@Nonnull
	public String getName()
	{
		return "machine_breeder";
	}

	@Override
	public void update()
	{
		if(this.getWorld().isRemote) return;
		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), new GameProfile(new UUID(0x12345678, 0x11223344), "sirenity:breeder"));
		EnumFacing facing = this.getWorld().getBlockState(this.getPos()).getValue(BlockMachineBase.FACING);
		AxisAlignedBB area = new AxisAlignedBB(this.getPos().offset(facing, radius + 1)).expand(radius, 0, radius);
		for(EntityAnimal entity : this.getWorld().getEntitiesWithinAABB(EntityAnimal.class, area))
		{
			if(!entity.isInLove() && entity.getGrowingAge() == 0)
			{
				for(int i = 1; i < this.inventory.size(); i++)
				{
					ItemStack stack = this.inventory.get(i);
					if(entity.isBreedingItem(stack))
					{
						entity.setInLove(player); // TODO: Add breeding cap
						stack.shrink(1);
						this.markDirty();
						break;
					}
				}
			}
		}
	}
}
