package net.xalcon.ecotec.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.api.components.IFluidItemInteraction;
import net.xalcon.ecotec.client.IItemRenderRegister;
import net.xalcon.ecotec.common.CreativeTabEcotec;
import net.xalcon.ecotec.common.init.ModCaps;
import net.xalcon.ecotec.common.tileentities.NbtSyncType;
import net.xalcon.ecotec.common.tileentities.TileEntityBase;

import javax.annotation.Nonnull;
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
		this.setUnlocalizedName(Ecotec.MODID + "." + internalName);
		this.setRegistryName(internalName);
		this.setCreativeTab(CreativeTabEcotec.Instance);
	}

	public ItemBlock createItemBlock()
	{
		return new ItemBlock(this);
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity te = worldIn.getTileEntity(pos);

		if(te != null)
		{
			if(te.hasCapability(ModCaps.getBucketInteractionCap(), facing))
			{
				IFluidItemInteraction bucketInteraction = te.getCapability(ModCaps.getBucketInteractionCap(), facing);
				if(bucketInteraction != null && bucketInteraction.interact(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ))
					return true;
			}
			if(te.hasCapability(ModCaps.getGuiProviderCap(), facing))
			{
				playerIn.openGui(Ecotec.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(worldIn.isRemote) return;

		if(!stack.hasTagCompound()) return;

		TileEntity tile = worldIn.getTileEntity(pos);
		if(!(tile instanceof TileEntityBase)) return;

		NBTTagCompound compound = stack.getSubCompound("eco:tile");
		if(compound != null)
			((TileEntityBase) tile).readSyncNbt(compound, NbtSyncType.BLOCK);
	}

	private ItemStack getItemDroppedWithNbt(IBlockState state, TileEntityBase tile, Random rand, int fortune)
	{
		Item item = this.getItemDropped(state, rand, fortune);
		if (item == Items.AIR) return ItemStack.EMPTY;

		ItemStack itemStack = new ItemStack(item, this.quantityDropped(rand), 0);
		NBTTagCompound compound = itemStack.getOrCreateSubCompound("eco:tile");
		tile.writeSyncNbt(compound, NbtSyncType.BLOCK);
		return itemStack;
	}

	@Nonnull
	@Override
	@SuppressWarnings("ArraysAsListWithZeroOrOneArgument") // we dont want any weired crashes because someone tries calling add() on this list
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityBase && ((TileEntityBase) te).saveNbtOnDrop())
		{
			Random rand = world instanceof World ? ((World)world).rand : RANDOM;
			ItemStack itemStack = this.getItemDroppedWithNbt(state, (TileEntityBase) te, rand, fortune);
			if(!itemStack.isEmpty())
				return Arrays.asList(itemStack);
		}
		return super.getDrops(world, pos, state, fortune);
	}

	@Override
	public void harvestBlock(@Nonnull World worldIn, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (te instanceof TileEntityBase && ((TileEntityBase) te).saveNbtOnDrop())
		{
			//noinspection ConstantConditions | this will never be null when we are getting called - otherwise, its a MC bug
			player.addStat(StatList.getBlockStats(this));
			player.addExhaustion(0.005F);

			if (worldIn.isRemote) return;

			int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
			ItemStack itemStack = this.getItemDroppedWithNbt(state, (TileEntityBase) te, worldIn.rand, fortune);
			spawnAsEntity(worldIn, pos, itemStack);
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
