package io.github.ititus.gimmestuff.inventory;

import com.google.common.base.Supplier;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

public class ItemHandlerInfiniteSingle implements IItemHandler {

	private final Supplier<ItemStack> stackSupplier;

	public ItemHandlerInfiniteSingle(Supplier<ItemStack> stackSupplier) {
		this.stackSupplier = stackSupplier;
	}

	@Override
	public int getSlots() {
		return 2;
	}

	public ItemStack getStackCopy() {
		return ItemStack.copyItemStack(stackSupplier.get());
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot != 0) {
			return null;
		}
		return getStackCopy();
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		ItemStack tileStack = stackSupplier.get();
		return tileStack == null || ItemStack.areItemsEqual(stack, tileStack) ? null : stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack stack = getStackCopy();
		if (stack != null) {
			stack.stackSize = Math.min(amount, stack.getMaxStackSize());
		}
		return stack;
	}
}
