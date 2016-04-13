package io.github.ititus.gimmestuff.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfiniteItem extends ItemBlock {

	public ItemBlockInfiniteItem(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	public static ItemStack getFilledStack(ItemStack toFill, ItemStack stack) {
		toFill = ItemStack.copyItemStack(toFill);
		if (toFill != null && stack != null) {
			ItemStack itemStack = stack.copy();
			itemStack.stackSize = Math.max(1, itemStack.getMaxStackSize() / 2);
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagCompound blockEntityTag = new NBTTagCompound();
			NBTTagCompound itemTag = new NBTTagCompound();

			itemStack.writeToNBT(itemTag);

			blockEntityTag.setTag("Item", itemTag);
			compound.setTag("BlockEntityTag", blockEntityTag);
			toFill.setTagCompound(compound);
		}
		return toFill;
	}

	public static ItemStack getStack(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound nbt = compound.getCompoundTag("BlockEntityTag");
				if (nbt.hasKey("Item", Constants.NBT.TAG_COMPOUND)) {
					return ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
				}
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		ItemStack itemStack = getStack(stack);
		if (itemStack == null) {
			tooltip.add(I18n.translateToLocal("text.gimmestuff:empty"));
		} else {
			EnumRarity rarity = itemStack.getRarity();
			tooltip.add(I18n.translateToLocalFormatted("text.gimmestuff:item", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + itemStack.getDisplayName()));
		}
	}
}
