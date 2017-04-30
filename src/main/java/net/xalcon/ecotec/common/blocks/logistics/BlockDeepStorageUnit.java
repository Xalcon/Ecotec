package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.xalcon.ecotec.Ecotec;
import net.xalcon.ecotec.common.GuiRegistry;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.CreativeTabEcotec;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

import javax.annotation.Nullable;

public class BlockDeepStorageUnit extends BlockBase implements IAutoRegisterTileEntity, ITileEntityProvider
{
	public BlockDeepStorageUnit()
	{
		super(Material.GROUND, "deep_storage_unit");
		this.setCreativeTab(CreativeTabEcotec.Instance);
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass()
	{
		return TileEntityDeepStorageUnit.class;
	}

	@Override
	public String getTileEntityRegistryName()
	{
		return this.getInternalName();
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
			playerIn.openGui(Ecotec.instance, GuiRegistry.DeepStorageUnit, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDeepStorageUnit();
	}
}
