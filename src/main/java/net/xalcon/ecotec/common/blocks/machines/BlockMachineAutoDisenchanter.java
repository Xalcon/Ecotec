package net.xalcon.ecotec.common.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.common.GuiRegistry;
import net.xalcon.ecotec.common.blocks.BlockMachineBase;
import net.xalcon.ecotec.common.tileentities.machines.TileEntityMachineAutoDisenchanter;

public class BlockMachineAutoDisenchanter extends BlockMachineBase
{
	public BlockMachineAutoDisenchanter()
	{
		super(Material.IRON, "machine_auto_disenchanter");
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineAutoDisenchanter();
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityMachineAutoDisenchanter.class;
	}

	@Override
	public boolean hasGui()
	{
		return true;
	}

	@Override
	public int getGuiId()
	{
		return GuiRegistry.MachineAutoDisenchanter;
	}
}
