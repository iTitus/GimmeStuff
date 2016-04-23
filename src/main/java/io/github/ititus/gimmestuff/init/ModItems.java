package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.item.ItemGodFood;
import io.github.ititus.gimmestuff.util.INameable;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

	public static ItemGodFood itemGodFood;

	public static void preInit() {
		itemGodFood = registerItem(new ItemGodFood());
	}

	private static <T extends Item & INameable> T registerItem(T item) {
		GameRegistry.register(item);
		return item;
	}

}
