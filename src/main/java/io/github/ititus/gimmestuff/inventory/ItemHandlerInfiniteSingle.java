package io.github.ititus.gimmestuff.inventory;

import io.github.ititus.gimmestuff.tile.TileInfiniteItem;

import net.minecraft.item.ItemStack;

import net.minecraftforge.items.IItemHandler;

public class ItemHandlerInfiniteSingle implements IItemHandler {

	private final TileInfiniteItem tile;

	public ItemHandlerInfiniteSingle(TileInfiniteItem tile) {
		this.tile = tile;
	}

	@Override
	public int getSlots() {
		return 1;
	}

	public ItemStack getStackCopy() {
		return ItemStack.copyItemStack(tile.getStack());
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return getStackCopy();
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return tile.getStack() == null || ItemStack.areItemsEqual(stack, tile.getStack()) ? null : stack;
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
