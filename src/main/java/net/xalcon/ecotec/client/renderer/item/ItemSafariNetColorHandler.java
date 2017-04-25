package net.xalcon.ecotec.client.renderer.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class ItemSafariNetColorHandler implements IItemColor
{
	public final static ItemSafariNetColorHandler Instance = new ItemSafariNetColorHandler();

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex)
	{
		NBTTagCompound itemNbt = stack.getTagCompound();
		if (itemNbt == null) return 0xFFFFFFFF;
		NBTTagCompound entityNbt = itemNbt.getCompoundTag("entity");
		String mobId = entityNbt.getString("id");
		if (mobId.isEmpty()) return 0xFFFFFFFF;
		ResourceLocation entityId = new ResourceLocation(mobId);
		EntityList.EntityEggInfo eggInfo = EntityList.ENTITY_EGGS.get(entityId);
		switch (tintIndex)
		{
			case 0: // inner core
				return eggInfo != null ? 0xFFFFFFFF : this.getPseudoRandomColor(entityId, tintIndex);
			case 1: // top half
				return eggInfo != null ? eggInfo.primaryColor : this.getPseudoRandomColor(entityId, tintIndex);
			case 2: // bottom half
				return eggInfo != null ? eggInfo.secondaryColor : this.getPseudoRandomColor(entityId, tintIndex);
		}
		return 0xFFFFFFFF;
	}

	private int getPseudoRandomColor(ResourceLocation entityId, int tintIndex)
	{
		int seed = 1;
		String mobId = entityId.getResourcePath();
		for (int i = 0; i < mobId.length(); i++)
		{
			seed *= mobId.charAt(i);
		}
		return 0xFF000000 | (seed >> tintIndex);
	}
}
