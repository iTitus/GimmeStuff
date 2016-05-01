package io.github.ititus.gimmestuff.inventory;

import io.github.ititus.gimmestuff.util.ISingleItemSupplier;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

public class ItemHandlerInfiniteSingle implements IItemHandler {

	private final ISingleItemSupplier singleItemSupplier;

	public ItemHandlerInfiniteSingle(ISingleItemSupplier singleItemSupplier) {
		this.singleItemSupplier = singleItemSupplier;
	}

	@Override
	public int getSlots() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot != 0) {
			return null;
		}
		return ItemStack.copyItemStack(singleItemSupplier.getItemStack());
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		ItemStack tileStack = singleItemSupplier.getItemStack();
		return tileStack == null || (ItemStack.areItemsEqual(stack, tileStack) && ItemStack.areItemStackTagsEqual(stack, tileStack)) ? null : stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack stack = ItemStack.copyItemStack(singleItemSupplier.getItemStack());
		if (stack != null) {
			stack.stackSize = Math.min(amount, stack.getMaxStackSize());
		}
		return stack;
	}
}
