package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.client.IItemRenderRegister;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.properties.EnumPipeConnection;
import net.xalcon.ecotec.common.blocks.properties.UnlistedPropertyObject;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityPipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPipe extends BlockBase implements IAutoRegisterTileEntity
{
	public final static UnlistedPropertyObject<TileEntityPipe> TILE_ENTITY_PROP = new UnlistedPropertyObject<>("tile", TileEntityPipe.class);

	public BlockPipe()
	{
		super(Material.IRON, "pipe");
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] {TILE_ENTITY_PROP});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		IExtendedBlockState exState = (IExtendedBlockState) super.getExtendedState(state, world, pos);
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityPipe)
			return exState.withProperty(TILE_ENTITY_PROP, (TileEntityPipe) te);
		return exState;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels(ItemBlock itemBlock, IItemRenderRegister register)
	{
		super.registerModels(itemBlock, register);

		ModelLoader.setCustomStateMapper(this, new StateMapperBase()
		{
			private final ModelResourceLocation MODEL_RESOURCE_LOCATION = new ModelResourceLocation(BlockPipe.this.getRegistryName(), null);

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state)
			{
				return MODEL_RESOURCE_LOCATION;
			}
		});
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityPipe.class;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityPipe();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote) return true;
		TileEntityPipe pipe = (TileEntityPipe) worldIn.getTileEntity(pos);
		playerIn.sendStatusMessage(new TextComponentString(String.format("U:%s|D:%s|N:%s|S:%s|W:%s|E:%s",
				pipe.getConnection(EnumFacing.UP) != EnumPipeConnection.DISCONNECTED,
				pipe.getConnection(EnumFacing.DOWN) != EnumPipeConnection.DISCONNECTED,
				pipe.getConnection(EnumFacing.NORTH) != EnumPipeConnection.DISCONNECTED,
				pipe.getConnection(EnumFacing.SOUTH) != EnumPipeConnection.DISCONNECTED,
				pipe.getConnection(EnumFacing.WEST) != EnumPipeConnection.DISCONNECTED,
				pipe.getConnection(EnumFacing.EAST) != EnumPipeConnection.DISCONNECTED)), false);
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		Ecotec.Log.info("neighborChanged");
		{
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityPipe && ((TileEntityPipe) te).updateConnections())
			{
				((TileEntityPipe) te).sendUpdate(false);
			}
		}
		{
			TileEntity te = world.getTileEntity(fromPos);
			if (te instanceof TileEntityPipe && ((TileEntityPipe) te).updateConnections())
			{
				((TileEntityPipe) te).sendUpdate(false);
			}
		}
		world.notifyBlockUpdate(pos, state, state, 3);
		world.notifyBlockUpdate(fromPos, state, state, 3);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		Ecotec.Log.info("getActualState");
		return super.getActualState(state, worldIn, pos);
	}
}
