package io.github.ititus.gimmestuff.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfiniteRF extends ItemBlock {

	public ItemBlockInfiniteRF(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	public static ItemStack getFilledStack(ItemStack stack, boolean hasEnergy) {
		stack = ItemStack.copyItemStack(stack);
		if (stack != null && hasEnergy) {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagCompound blockEntityTag = new NBTTagCompound();
			NBTTagCompound energyTag = new NBTTagCompound();

			energyTag.setBoolean("hasEnergy", hasEnergy);

			blockEntityTag.setTag("Energy", energyTag);
			compound.setTag("BlockEntityTag", blockEntityTag);
			stack.setTagCompound(compound);
		}
		return stack;
	}

	public static boolean hasEnergy(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound nbt = compound.getCompoundTag("BlockEntityTag");
				if (nbt.hasKey("Energy", Constants.NBT.TAG_COMPOUND)) {
					return nbt.getCompoundTag("Energy").getBoolean("hasEnergy");
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		if (!hasEnergy(stack)) {
			tooltip.add(I18n.translateToLocal("text.gimmestuff:empty"));
		} else {
			tooltip.add(I18n.translateToLocal("text.gimmestuff:energy"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		subItems.add(getFilledStack(new ItemStack(item), false));
		subItems.add(getFilledStack(new ItemStack(item), true));
	}

}
