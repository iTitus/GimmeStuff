package io.github.ititus.gimmestuff.item;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModCreativeTab;
import io.github.ititus.gimmestuff.util.INameable;

import net.minecraft.item.Item;

public class ItemBase extends Item implements INameable {

	protected final String name;

	public ItemBase(String name) {
		super();
		this.name = name;
		setUnlocalizedName(GimmeStuff.MOD_ID + ":" + name);
		setRegistryName(GimmeStuff.MOD_ID, name);
		setCreativeTab(ModCreativeTab.MAIN_TAB);
	}

	@Override
	public String getName() {
		return name;
	}
}
