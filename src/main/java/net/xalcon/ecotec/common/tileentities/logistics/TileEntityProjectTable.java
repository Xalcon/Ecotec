package net.xalcon.ecotec.common.tileentities.logistics;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.container.InventoryCraftingProxy;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderProjectTable;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

public class TileEntityProjectTable extends TileEntityBase
{
	private ComponentItemHandler inventory;
	private ItemStackHandler craftMatrix = new ItemStackHandler(9)
	{
		@Override
		protected void onLoad()
		{
			craftOutput.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrixProxy, TileEntityProjectTable.this.getWorld()));
		}

		@Override
		protected void onContentsChanged(int slot)
		{
			ItemStack stack = CraftingManager.getInstance().findMatchingRecipe(craftMatrixProxy, TileEntityProjectTable.this.getWorld());
			craftOutput.setInventorySlotContents(0, stack);

		}
	};
	private InventoryCraftingProxy<ItemStackHandler> craftMatrixProxy = new InventoryCraftingProxy<>(this.craftMatrix, 3, 3);
	private InventoryCraftResult craftOutput = new InventoryCraftResult();

	public TileEntityProjectTable()
	{
		this.inventory = this.addComponent(new ComponentItemHandler(18));
		this.addComponent(new GuiProviderProjectTable());
	}

	public ItemStackHandler getCraftMatrixHander()
	{
		return this.craftMatrix;
	}
	public InventoryCrafting getCraftMatrixProxy()
	{
		return this.craftMatrixProxy;
	}
	public InventoryCraftResult getCraftOutputHandler()
	{
		return this.craftOutput;
	}

	@Override
	public void readSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.readSyncNbt(nbt, type);
		this.craftMatrix.deserializeNBT(nbt.getCompoundTag("eco:cmat"));
	}

	@Override
	public void writeSyncNbt(NBTTagCompound nbt, NbtSyncType type)
	{
		super.writeSyncNbt(nbt, type);
		nbt.setTag("eco:cmat", this.craftMatrix.serializeNBT());
	}
}
