package net.xalcon.minefactory.common.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public abstract class TileEntityBase extends TileEntity
{
	private String customDisplayName;

	public void setCustomDisplayName(String customName)
	{
		this.customDisplayName = customName;
	}

	public String getCustomDisplayName()
	{
		return this.customDisplayName;
	}

	public abstract String getUnlocalizedName();

	@Nonnull
	@Override
	public ITextComponent getDisplayName()
	{
		return this.customDisplayName != null
				? new TextComponentString(this.customDisplayName)
				: new TextComponentTranslation("tile." + this.getUnlocalizedName());
	}
}
