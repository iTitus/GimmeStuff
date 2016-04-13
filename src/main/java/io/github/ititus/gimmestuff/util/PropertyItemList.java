package io.github.ititus.gimmestuff.util;

import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyItemList implements IUnlistedProperty<ItemList> {

	private final String name;

	public PropertyItemList(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(ItemList value) {
		return value != null;
	}

	@Override
	public Class<ItemList> getType() {
		return ItemList.class;
	}

	@Override
	public String valueToString(ItemList value) {
		return value.toString();
	}

}
