package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.client.IItemRenderRegister;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.properties.EnumTankBlockType;
import net.xalcon.ecotec.common.items.ItemBlockFluidTank;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityTankSlave;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityTankValve;
import net.xalcon.ecotec.common.multiblock.IEcotecMultiBlock;

import javax.annotation.Nullable;

public class BlockFluidTank extends BlockBase implements ITileEntityProvider
{
	public final static PropertyEnum<EnumTankBlockType> TANK_BLOCK_TYPE = PropertyEnum.create("type", EnumTankBlockType.class);
	public BlockFluidTank()
	{
		super(Material.IRON, "fluid_tank");
	}

	@Override
	public ItemBlock createItemBlock()
	{
		return new ItemBlockFluidTank(this);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return meta == EnumTankBlockType.VALVE.getMeta()
				? new TileEntityTankValve()
				: new TileEntityTankSlave();
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for(EnumTankBlockType type : EnumTankBlockType.values())
			list.add(new ItemStack(itemIn, 1, type.getMeta()));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.blockState.getBaseState().withProperty(TANK_BLOCK_TYPE, EnumTankBlockType.fromMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TANK_BLOCK_TYPE).getMeta();
	}

	@Override
	public void registerItemModels(ItemBlock itemBlock, IItemRenderRegister register)
	{
		for(EnumTankBlockType type : EnumTankBlockType.values())
			register.registerItemRenderer(itemBlock, type.getMeta(), this.getRegistryName(), "type=" + type.getName());
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(this, 1, state.getValue(TANK_BLOCK_TYPE).getMeta());
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return state.getValue(TANK_BLOCK_TYPE).isOpaque();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, TANK_BLOCK_TYPE);
	}

	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
	{
		return state.getValue(TANK_BLOCK_TYPE).getLayer() == layer;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof IEcotecMultiBlock)
		{
			long startTime = System.nanoTime();
			((IEcotecMultiBlock) tile).tryFormMutliblock();
			long delta = System.nanoTime() - startTime;
			Ecotec.Log.info("Form lookup took " + delta + "ns");
		}
		return false;
	}

	@Override
	public int getLightOpacity(IBlockState state)
	{
		return 0;
	}
}
