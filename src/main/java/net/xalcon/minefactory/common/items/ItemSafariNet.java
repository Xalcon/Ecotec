package net.xalcon.minefactory.common.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.asm.transformers.ItemStackTransformer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSafariNet extends ItemBase
{
	public ItemSafariNet()
	{
		super("safari_net");
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		if(!playerIn.getEntityWorld().isRemote)
		{
			NBTTagCompound compound = new NBTTagCompound();
			target.writeToNBT(compound);
			stack.setStackDisplayName(target.getName());
			compound.setString("id", EntityList.getKey(target).getResourcePath());
			stack.setTagInfo("entity", compound);
			playerIn.getEntityWorld().removeEntity(target);
			return true;
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote) return EnumActionResult.SUCCESS;
		ItemStack itemStack = player.getHeldItem(hand);
		try
		{
			NBTTagCompound nbt = itemStack.getTagCompound().getCompoundTag("entity");
			Entity entity = EntityList.createEntityFromNBT(nbt, worldIn);
			if(entity != null)
			{
				AxisAlignedBB bb = entity.getEntityBoundingBox();
				entity.setLocationAndAngles(pos.getX() + (bb.maxX - bb.minX) * 0.5,
						pos.getY() + 1 + (bb.maxY - bb.minY) * 0.5,
						pos.getZ() + (bb.maxZ - bb.minZ) * 0.5,
						worldIn.rand.nextFloat() * 360.0F, 0.0F);

				worldIn.spawnEntity(entity);
			}
			itemStack.getTagCompound().removeTag("entity");
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}

		return EnumActionResult.SUCCESS;
	}
}
