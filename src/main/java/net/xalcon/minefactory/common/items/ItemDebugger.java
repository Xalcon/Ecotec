package net.xalcon.minefactory.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemDebugger extends ItemBase
{
	public ItemDebugger()
	{
		super("debugger");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = worldIn.getBlockState(pos.north());
		Block block = state.getBlock();
		if(block instanceof BlockCrops)
		{
			BlockCrops crops = (BlockCrops) block;
			if(crops.isMaxAge(state))
			{
				
			}
		}
		player.sendStatusMessage(new TextComponentString(state.toString()), true);
		return EnumActionResult.SUCCESS;
	}
}
