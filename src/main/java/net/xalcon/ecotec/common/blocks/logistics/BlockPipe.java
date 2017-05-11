package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.xalcon.ecotec.client.IItemRenderRegister;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.blocks.properties.UnlistedPropertyObject;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityPipe;

import javax.annotation.Nonnull;

public class BlockPipe extends BlockBase
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
}
