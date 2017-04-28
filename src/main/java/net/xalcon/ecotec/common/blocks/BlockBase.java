package net.xalcon.ecotec.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.client.IItemRenderRegister;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class BlockBase extends Block
{
	private String internalName;

	public BlockBase(Material materialIn, String internalName)
	{
		super(materialIn);
		this.internalName = internalName;
		this.setUnlocalizedName(internalName);
		this.setRegistryName(internalName);
	}

	public void registerItemModels(ItemBlock itemBlock, IItemRenderRegister register)
	{
		//noinspection ConstantConditions
		register.registerItemRenderer(itemBlock, 0, this.getRegistryName(), "inventory");
	}

	public String getInternalName()
	{
		return this.internalName;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(!stack.hasTagCompound()) return;

		TileEntity tile = worldIn.getTileEntity(pos);
		if(!(tile instanceof TileEntityBase)) return;

		NBTTagCompound compound = stack.getSubCompound("eco:tile");
		if(compound != null)
			((TileEntityBase) tile).readSyncNbt(compound, TileEntityBase.NbtSyncType.BLOCK);
	}

	@Override
	@SuppressWarnings("ArraysAsListWithZeroOrOneArgument") // we dont want any weired crashes because someone tries calling add() on this list
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityBase && ((TileEntityBase) te).saveNbtOnDrop())
		{
			Random rand = world instanceof World ? ((World)world).rand : RANDOM;
			Item item = this.getItemDropped(state, rand, fortune);
			if(item instanceof ItemBlock)
			{
				NBTTagCompound compound = new NBTTagCompound();
				((TileEntityBase) te).writeSyncNbt(compound, TileEntityBase.NbtSyncType.BLOCK);
				return Arrays.asList(new ItemStack(item, 1, 0, compound));
			}
		}
		return super.getDrops(world, pos, state, fortune);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
