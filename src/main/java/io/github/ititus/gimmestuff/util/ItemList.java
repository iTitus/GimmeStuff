package io.github.ititus.gimmestuff.util;

import java.util.Objects;

import com.google.common.collect.ImmutableList;

import net.minecraft.item.ItemStack;

public class ItemList {

	private final ImmutableList<ItemStack> items;

	public ItemList(ImmutableList<ItemStack> items) {
		this.items = items;
	}

	public static Builder builder() {
		return new Builder();
	}

	public ImmutableList<ItemStack> getItems() {
		return items;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof ItemList)) {
			return false;
		}
		ItemList itemList = (ItemList) o;
		return Objects.equals(items, itemList.items);
	}

	@Override
	public int hashCode() {
		return Objects.hash(items);
	}

	@Override
	public String toString() {
		return "ItemList{" +
				"items=" + items +
				'}';
	}

	public static class Builder {

		private final ImmutableList.Builder<ItemStack> items;

		public Builder() {
			this.items = ImmutableList.builder();
		}

		public void add(ItemStack stack) {
			items.add(stack);
		}

		public ItemList build() {
			return new ItemList(items.build());
		}

	}
}
