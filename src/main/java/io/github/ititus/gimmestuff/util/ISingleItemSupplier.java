package io.github.ititus.gimmestuff.util;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface ISingleItemSupplier extends IItemSupplier {

	@Override
	default int getItemStackCount() {
		return 1;
	}

	@Override
	default ItemStack getItemStack(int index) {
		return getItemStack();
	}

	ItemStack getItemStack();


}
