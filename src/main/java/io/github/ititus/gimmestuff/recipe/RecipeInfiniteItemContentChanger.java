package io.github.ititus.gimmestuff.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;
import io.github.ititus.gimmestuff.util.Utils;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import scala.actors.threadpool.Arrays;

public class RecipeInfiniteItemContentChanger implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		int foundStacks = 0;
		ItemStack stackToFill = null;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() instanceof ItemBlockInfiniteItem) {
					if (stackToFill != null) {
						return false;
					}
					stackToFill = stack.copy();
				} else {
					foundStacks++;
				}
			}
		}

		if (stackToFill == null || foundStacks == 0) {
			return false;
		}

		ItemStack[] stacks = ItemBlockInfiniteItem.getStacks(stackToFill);
		BlockInfiniteItem.InfiniteItemType type = BlockInfiniteItem.InfiniteItemType.byMeta(stackToFill.getMetadata());

		return (foundStacks == 1 && type == BlockInfiniteItem.InfiniteItemType.SINGLE && Utils.containsOnlyNull(stacks)) || (foundStacks >= 1 && type == BlockInfiniteItem.InfiniteItemType.MULTI);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		List<ItemStack> newStacks = Lists.newArrayList();
		ItemStack stackToFill = null;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() instanceof ItemBlockInfiniteItem) {
					if (stackToFill != null) {
						return null;
					}
					stackToFill = stack.copy();
				} else {
					newStacks.add(stack.copy());
				}
			}
		}

		if (newStacks.isEmpty() || stackToFill == null) {
			return null;
		}

		BlockInfiniteItem.InfiniteItemType type = BlockInfiniteItem.InfiniteItemType.byMeta(stackToFill.getMetadata());
		ItemStack[] existingStacks = ItemBlockInfiniteItem.getStacks(stackToFill);

		if (type == BlockInfiniteItem.InfiniteItemType.SINGLE && (newStacks.size() > 0 || !Utils.containsOnlyNull(existingStacks))) {
			return null;
		}

		if (type == BlockInfiniteItem.InfiniteItemType.MULTI) {
			List<ItemStack> list = Lists.newArrayList();

			if (existingStacks != null) {
				Utils.mergeItemStackLists(list, Arrays.asList(existingStacks));
			}
			Utils.mergeItemStackLists(list, newStacks);

			list.sort((stack1, stack2) -> Integer.compare(Item.getIdFromItem(stack1.getItem()), Item.getIdFromItem(stack2.getItem())));

			newStacks = list;
		}

		return ItemBlockInfiniteItem.getFilledStack(stackToFill, newStacks.toArray(new ItemStack[newStacks.size()]));

	}

	@Override
	public int getRecipeSize() {
		return 10;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] stacks = new ItemStack[inv.getSizeInventory()];

		for (int i = 0; i < stacks.length; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && !(stack.getItem() instanceof ItemBlockInfiniteItem)) {
				stacks[i] = stack.copy();
				stacks[i].stackSize = 1;
			}
		}

		return stacks;
	}

}
