package io.github.ititus.gimmestuff.inventory;

import io.github.ititus.gimmestuff.util.IItemSupplier;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

public class ItemHandlerInfiniteMulti implements IItemHandler {

	private final IItemSupplier itemSupplier;

	public ItemHandlerInfiniteMulti(IItemSupplier itemSupplier) {
		this.itemSupplier = itemSupplier;
	}

	@Override
	public int getSlots() {
		return itemSupplier.getItemStackCount() * 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < 0 || slot >= getSlots() || slot % 2 == 1) {
			return null;
		}
		return ItemStack.copyItemStack(itemSupplier.getItemStack(slot / 2));
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot < 0 || slot >= getSlots() || slot % 2 == 0) {
			return stack;
		}
		ItemStack tileStack = itemSupplier.getItemStack(slot / 2);
		return tileStack == null || ItemStack.areItemsEqual(stack, tileStack) ? null : stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (slot < 0 || slot >= getSlots() || slot % 2 == 1) {
			return null;
		}
		ItemStack stack = ItemStack.copyItemStack(itemSupplier.getItemStack(slot / 2));
		if (stack != null) {
			stack.stackSize = Math.min(amount, stack.getMaxStackSize());
		}
		return stack;
	}
}
