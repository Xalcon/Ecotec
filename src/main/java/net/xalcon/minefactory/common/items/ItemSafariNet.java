package net.xalcon.minefactory.common.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ItemSafariNet extends ItemBase
{
	private boolean isMultiuse;

	public ItemSafariNet(boolean isMultiuse)
	{
		super(!isMultiuse ? "safari_net_single" : "safari_net_multi");
		this.isMultiuse = isMultiuse;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		if(!playerIn.getEntityWorld().isRemote)
		{
			NBTTagCompound compound = new NBTTagCompound();
			target.writeToNBT(compound);
			compound.setString("id", EntityList.getKey(target).getResourcePath());
			compound.setString("mfr:hoverEntityName", target.getName());
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
				if(!this.isMultiuse) // this is equal to "if((ItemSafariNet)itemStack.getItem()).isMultiuse)"
					itemStack.shrink(1);
			}
			itemStack.getTagCompound().removeTag("entity");
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}

		return EnumActionResult.SUCCESS;
	}

	@Override
	public String getHighlightTip(ItemStack stack, String displayName)
	{
		NBTTagCompound itemNbt = stack.getTagCompound();
		if(itemNbt != null)
		{
			NBTTagCompound entityNbt = itemNbt.getCompoundTag("entity");
			String entityName = entityNbt.getString("mfr:hoverEntityName").trim();
			if(!entityName.isEmpty())
				return super.getHighlightTip(stack, displayName) + " (" + entityName + ")";
		}

		return super.getHighlightTip(stack, displayName);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		NBTTagCompound itemNbt = stack.getTagCompound();
		if(itemNbt != null)
		{
			NBTTagCompound entityNbt = itemNbt.getCompoundTag("entity");
			String entityName = entityNbt.getString("mfr:hoverEntityName").trim();
			if(!entityName.isEmpty())
				tooltip.add(I18n.format("tooltip.safari_net.captured_entity", entityName));
		}
	}
}
