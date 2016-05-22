package io.github.ititus.gimmestuff.util;

import java.util.List;

import net.minecraft.item.ItemStack;

public class Utils {

	public static <T> boolean containsOnlyNull(T[] array) {
		return countNonNull(array) == 0;
	}

	public static <T> int countNonNull(T[] array) {
		int count = 0;
		if (array != null) {
			for (T element : array) {
				if (element != null) {
					count++;
				}
			}
		}
		return count;
	}

	public static void mergeItemStackLists(List<ItemStack> to, Iterable<ItemStack> from) {
		fromLoop:
		for (ItemStack toAdd : from) {
			if (toAdd != null) {
				for (ItemStack existingStack : to) {
					if (existingStack != null && ItemStack.areItemsEqual(toAdd, existingStack) && ItemStack.areItemStackTagsEqual(toAdd, existingStack)) {
						break fromLoop;
					}
				}
				to.add(toAdd);
			}
		}
	}

}
