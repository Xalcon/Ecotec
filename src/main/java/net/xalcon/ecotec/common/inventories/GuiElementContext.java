package net.xalcon.ecotec.common.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

public class GuiElementContext<T extends TileEntityBase>
{
	private BlockPos pos;
	private World world;
	private EntityPlayer player;
	private Class<T> tileEntityClass;

	public BlockPos getPos()
	{
		return this.pos;
	}
	public World getWorld() { return this.world; }
	public EntityPlayer getPlayer()
	{
		return this.player;
	}

	public T getTileEntity()
	{
		return this.tileEntityClass.cast(this.world.getTileEntity(this.pos));
	}

	public GuiElementContext(EntityPlayer player, World world, int x, int y, int z, Class<T> tileEntityClass)
	{
		this.player = player;
		this.world = world;
		this.pos = new BlockPos(x, y, z);
		this.tileEntityClass = tileEntityClass;
	}
}
