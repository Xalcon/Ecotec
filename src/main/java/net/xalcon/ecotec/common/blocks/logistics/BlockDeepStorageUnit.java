package net.xalcon.ecotec.common.blocks.logistics;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.xalcon.ecotec.common.blocks.BlockBase;
import net.xalcon.ecotec.common.creativetabs.CreativeTabEcotecMachines;
import net.xalcon.ecotec.common.tileentities.IAutoRegisterTileEntity;
import net.xalcon.ecotec.common.tileentities.logistics.TileEntityDeepStorageUnit;

import javax.annotation.Nullable;

public class BlockDeepStorageUnit extends BlockBase implements IAutoRegisterTileEntity, ITileEntityProvider
{
	public BlockDeepStorageUnit()
	{
		super(Material.GROUND, "deep_storage_unit");
		this.setCreativeTab(CreativeTabEcotecMachines.Instance);
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
		if (worldIn.isRemote) return true;

		TileEntityDeepStorageUnit dsu = (TileEntityDeepStorageUnit) worldIn.getTileEntity(pos);
		if (dsu == null) return true;

		IItemHandler itemHandler =  dsu.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		if(itemHandler == null) return true;

		if(playerIn.isSneaking())
		{
			ItemStack itemStack = itemHandler.getStackInSlot(0);
			playerIn.sendStatusMessage(new TextComponentString(itemStack.toString()), true);
			return true;
		}

		ItemStack stack = playerIn.getHeldItemMainhand();
		if (stack.isEmpty())
		{

			ItemStack itemStack = itemHandler.extractItem(0, 64, false);
			if(!itemStack.isEmpty())
			{
				playerIn.inventory.addItemStackToInventory(itemStack);
			}
		}
		else
		{
			dsu.insertItemStack(stack);
		}
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityDeepStorageUnit();
	}
}
