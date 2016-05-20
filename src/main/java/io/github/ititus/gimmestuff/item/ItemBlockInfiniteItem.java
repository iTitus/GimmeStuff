package io.github.ititus.gimmestuff.item;

import java.util.List;

import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfiniteItem extends ItemBlock {

	public ItemBlockInfiniteItem(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	public static ItemStack getFilledStack(ItemStack toFill, ItemStack[] stacks) {
		toFill = ItemStack.copyItemStack(toFill);
		if (toFill != null && stacks != null && stacks.length > 0) {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagCompound blockEntityTag = new NBTTagCompound();
			NBTTagCompound itemsTag = new NBTTagCompound();

			itemsTag.setInteger("size", stacks.length);
			NBTTagList itemList = new NBTTagList();
			for (int i = 0; i < stacks.length; i++) {
				ItemStack stack = stacks[i];
				if (stack != null) {
					stack.stackSize = stack.getMaxStackSize();
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("index", i);
					stack.writeToNBT(tag);
					itemList.appendTag(tag);
				}
			}

			itemsTag.setTag("ItemList", itemList);
			blockEntityTag.setTag("Items", itemsTag);
			compound.setTag("BlockEntityTag", blockEntityTag);
			toFill.setTagCompound(compound);
		}
		return toFill;
	}

	public static ItemStack[] getStacks(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound blockEntityTag = compound.getCompoundTag("BlockEntityTag");
				if (blockEntityTag.hasKey("Items", Constants.NBT.TAG_COMPOUND)) {
					NBTTagCompound itemsTag = blockEntityTag.getCompoundTag("Items");
					ItemStack[] stacks = new ItemStack[itemsTag.getInteger("size")];
					if (itemsTag.hasKey("ItemList", Constants.NBT.TAG_LIST)) {
						NBTTagList itemList = itemsTag.getTagList("ItemList", Constants.NBT.TAG_COMPOUND);
						for (int i = 0; i < itemList.tagCount(); i++) {
							NBTTagCompound tag = itemList.getCompoundTagAt(i);

							int index = tag.getInteger("index");
							ItemStack itemStack = ItemStack.loadItemStackFromNBT(tag);

							if (itemStack != null) {
								itemStack.stackSize = stack.getMaxStackSize();
								stacks[index] = itemStack;
							}
						}
					}
					return stacks;
				}
			}
		}
		return null;
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + BlockInfiniteItem.InfiniteItemType.byMeta(stack.getMetadata()).getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		ItemStack[] stacks = getStacks(stack);
		int count = Utils.countNonNull(stacks);

		if (count == 0) {
			tooltip.add(I18n.format("text.gimmestuff:empty"));
		} else if (count == 1) {
			for (int i = 0; i < stacks.length; i++) {
				ItemStack itemStack = stacks[i];
				if (itemStack != null) {
					EnumRarity rarity = itemStack.getRarity();
					tooltip.add(I18n.format("text.gimmestuff:item", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + itemStack.getDisplayName()));
					break;
				}
			}
		} else {
			tooltip.add(I18n.format("text.gimmestuff:items"));
			for (int i = 0; i < stacks.length; i++) {
				ItemStack itemStack = stacks[i];
				if (itemStack != null) {
					EnumRarity rarity = itemStack.getRarity();
					tooltip.add("  - " + (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + itemStack.getDisplayName());
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		for (BlockInfiniteItem.InfiniteItemType type : BlockInfiniteItem.InfiniteItemType.VALUES) {
			subItems.add(new ItemStack(item, 1, type.getMeta()));
		}
	}
}
