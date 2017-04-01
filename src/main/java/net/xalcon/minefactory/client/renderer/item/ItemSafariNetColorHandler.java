package net.xalcon.minefactory.client.renderer.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemSafariNetColorHandler implements IItemColor
{
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		if(!stack.hasTagCompound()) return 0xFFFFFFFF;
		NBTTagCompound nbt = stack.getTagCompound().getCompoundTag("entity");
		String mobId = nbt.getString("id");
		if(mobId.equals("")) return 0xFFFFFFFF;
		ResourceLocation entityId = new ResourceLocation(mobId);
		EntityList.EntityEggInfo eggInfo = EntityList.ENTITY_EGGS.get(entityId);
		switch(tintIndex)
		{
			case 0: // inner core
				return eggInfo != null ? 0xFFFFFFFF : getRandomColor(entityId, tintIndex);
			case 1: // top half
				return eggInfo != null ? eggInfo.primaryColor : getRandomColor(entityId, tintIndex);
			case 2: // bottom half
				return eggInfo != null ? eggInfo.secondaryColor : getRandomColor(entityId, tintIndex);
		}
		return 0xFFFFFFFF;
	}

	private int getRandomColor(ResourceLocation entityId, int tintIndex)
	{
		int seed = 1;
		String mobId = entityId.getResourcePath();
		for(int i = 0; i < mobId.length(); i++)
		{
			seed *= mobId.charAt(i);
		}
		return 0xFF000000 | (seed >> tintIndex);
	}
}
