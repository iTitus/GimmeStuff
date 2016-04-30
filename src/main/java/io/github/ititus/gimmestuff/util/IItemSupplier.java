package io.github.ititus.gimmestuff.util;

import net.minecraft.item.ItemStack;

public interface IItemSupplier {

	int getItemStackCount();

	ItemStack getItemStack(int index);

}
