package net.xalcon.ecotec.common.tileentities.agriculture;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemDropoff;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderFisher;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class TileEntityMachineFisher extends TileEntityTickable
{
	private final static GameProfile FISHER_PROFLE = new GameProfile(UUID.fromString("a423c7b7-ac41-4be4-81aa-1c27d43cf005"), "ecotec:fisher");
	private final static EnumFacing[] WATER_LOCATIONS = { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST };
	private final ComponentEnergyStorage energyStorage;
	private final ComponentItemHandler inventory;
	private final ComponentItemDropoff itemDropoff;

	@Override
	public int getMaxIdleTime()
	{
		return 20;
	}

	public TileEntityMachineFisher()
	{
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.inventory = this.addComponent(new ComponentItemHandler(1));
		this.itemDropoff = this.addComponent(new ComponentItemDropoff());
		this.addComponent(new GuiProviderFisher());
	}

	@Override
	protected boolean doWork()
	{
		if(!(this.itemDropoff.isClogged() && this.itemDropoff.tryDropCloggedItems()) || this.energyStorage.getEnergyStored() < 2000 || !this.isInWater()) return false;

		FakePlayer player = FakePlayerFactory.get((WorldServer) this.getWorld(), FISHER_PROFLE);

		LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
		lootcontext$builder.withLuck(player.getLuck());
		List<ItemStack> result = this.getWorld().getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.getWorld().rand, lootcontext$builder.build());

		BlockPos bobberPos = this.getPos().north();
		EntityFishHook hookEntity = new EntityFishHook(this.getWorld(), player, bobberPos.getX() + 0.5f, bobberPos.getY(), bobberPos.getZ() + 0.5f);
		ItemFishedEvent event = new net.minecraftforge.event.entity.player.ItemFishedEvent(result, 1, hookEntity);
		net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		hookEntity.setDead();

		if(event.isCanceled())
			return false;

		this.itemDropoff.dropItems(event.getDrops());
		this.energyStorage.useEnergy(2000);
		return false;
	}

	private boolean isInWater()
	{
		for(EnumFacing f : WATER_LOCATIONS)
		{
			BlockPos checkPos = this.getPos().offset(f);
			Fluid fluid  = this.getFluidFromBlock(this.getWorld().getBlockState(checkPos));
			if(fluid != null)
			{
				int temp = fluid.getTemperature(this.getWorld(), checkPos);
				if(temp > 400) return false;
			}
		}
		return true;
	}

	@Nullable
	public Fluid getFluidFromBlock(IBlockState blockState)
	{
		Block block = blockState.getBlock();
		if(block instanceof IFluidBlock)
			return ((IFluidBlock) block).getFluid();
		return blockState.getMaterial() == Material.WATER ? FluidRegistry.WATER : null;
	}
}
