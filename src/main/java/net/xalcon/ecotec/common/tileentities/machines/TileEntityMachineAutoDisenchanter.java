package net.xalcon.ecotec.common.tileentities.machines;

import com.mojang.authlib.GameProfile;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.xalcon.ecotec.common.components.ComponentEnergyStorage;
import net.xalcon.ecotec.common.components.ComponentItemHandler;
import net.xalcon.ecotec.common.components.ComponentItemHandlerDisenchanter;
import net.xalcon.ecotec.common.container.guiprovider.GuiProviderAutoDisenchanter;
import net.xalcon.ecotec.common.tileentities.TileEntityTickable;

import java.util.UUID;

public class TileEntityMachineAutoDisenchanter extends TileEntityTickable
{
	static GameProfile DISENCHANTER_PLAYER = new GameProfile(UUID.fromString("16316878-e346-4e7c-9668-93cac717cb16"), "ecotec:auto_disenchanter");
	private final ComponentEnergyStorage energyStorage;
	private final ComponentItemHandler inventory;

	public TileEntityMachineAutoDisenchanter()
	{
		this.inventory = this.addComponent(new ComponentItemHandlerDisenchanter());
		this.energyStorage = this.addComponent(new ComponentEnergyStorage(512, 0, 16000));
		this.addComponent(new GuiProviderAutoDisenchanter());
	}

	@Override
	protected boolean doWork()
	{
		ItemStack disItem = this.inventory.getStackInSlot(0);
		ItemStack bookItem = this.inventory.getStackInSlot(1);
		if(disItem.isEmpty() || bookItem.isEmpty() || bookItem.getItem() != Items.BOOK || !this.inventory.getStackInSlot(2).isEmpty() || !this.inventory.getStackInSlot(3).isEmpty())
			return false;

		this.inventory.setStackInSlot(0, ItemStack.EMPTY);

		NBTTagList list = disItem.getEnchantmentTagList();
		if(!disItem.isItemEnchanted() || list == null)
		{
			// if the item is not enchanted (however it got in here), move it directly to the output
			this.inventory.setStackInSlot(2, disItem);
			return true;
		}

		if(this.energyStorage.getEnergyStored() < 8000) return false;

		bookItem.shrink(1);
		this.inventory.setStackInSlot(1, bookItem);
		NBTTagCompound tag = (NBTTagCompound) list.removeTag(0);
		Enchantment enchantment = Enchantment.getEnchantmentByID(tag.getInteger("id"));
		if(enchantment != null)
		{
			ItemStack enchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
			Items.ENCHANTED_BOOK.addEnchantment(enchantedBook, new EnchantmentData(enchantment, tag.getInteger("lvl")));
			this.inventory.setStackInSlot(3, enchantedBook);
		}

		// deal between 15-30% of item max damage to the item
		int dmg = (int) ((this.getWorld().rand.nextFloat() / 2f + 0.5f) * (disItem.getMaxDamage() * 0.3f));
		disItem.damageItem(dmg, FakePlayerFactory.get((WorldServer) this.getWorld(), DISENCHANTER_PLAYER));

		if(!disItem.isEmpty())
		{
			this.inventory.setStackInSlot(2, disItem);
		}

		this.energyStorage.useEnergy(8000);

		return false;
	}
}
