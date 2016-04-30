package io.github.ititus.gimmestuff.recipe;

import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeInfiniteItemContentChanger implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean foundStack = false;
		boolean hasStacksToFill = false;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() instanceof ItemBlockInfiniteItem && stack.getMetadata() == BlockInfiniteItem.InfiniteItemType.SINGLE.getMeta()) {
					hasStacksToFill = true;
				} else {
					if (foundStack) {
						return false;
					}
					foundStack = true;
				}
			}
		}

		return foundStack && hasStacksToFill;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack stack1 = null;
		int stacksToFill = 0;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null) {
				if (stack.getItem() instanceof ItemBlockInfiniteItem && stack.getMetadata() == BlockInfiniteItem.InfiniteItemType.SINGLE.getMeta()) {
					stacksToFill++;
				} else {
					if (stack1 != null) {
						return null;
					}
					stack1 = stack;
				}
			}
		}

		if (stack1 == null || stacksToFill <= 0) {
			return null;
		}

		return ItemBlockInfiniteItem.getFilledStack(new ItemStack(ModBlocks.blockInfiniteItem, stacksToFill, BlockInfiniteItem.InfiniteItemType.SINGLE.getMeta()), stack1);
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
			if (stack != null && !(stack.getItem() instanceof ItemBlockInfiniteItem && stack.getMetadata() == BlockInfiniteItem.InfiniteItemType.SINGLE.getMeta())) {
				stacks[i] = stack.copy();
				stacks[i].stackSize = 1;
			}
		}

		return stacks;
	}

}
