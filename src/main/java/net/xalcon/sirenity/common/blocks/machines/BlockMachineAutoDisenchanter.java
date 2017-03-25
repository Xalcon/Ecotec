package net.xalcon.sirenity.common.blocks.machines;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.sirenity.common.blocks.BlockMachineBase;
import net.xalcon.sirenity.common.tileentities.TileEntityMachineAutoDisenchanter;

import javax.annotation.Nullable;

public class BlockMachineAutoDisenchanter extends BlockMachineBase implements ITileEntityProvider
{
	public BlockMachineAutoDisenchanter()
	{
		super(Material.IRON, "machine_auto_disenchanter");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityMachineAutoDisenchanter();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(worldIn.isRemote) return true;

		ItemStack itemStack = playerIn.getHeldItemMainhand();
		if(itemStack.isEmpty() || !itemStack.isItemEnchanted()) return true;
		NBTTagList list = itemStack.getEnchantmentTagList();
		NBTTagCompound tag = (NBTTagCompound) list.removeTag(0);
		try
		{
			ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
			Items.ENCHANTED_BOOK.addEnchantment(book, new EnchantmentData(Enchantment.getEnchantmentByID(tag.getInteger("id")), tag.getInteger("lvl")));
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), book);
		}
		catch(Exception e)
		{

		}
		return true;
	}
}
