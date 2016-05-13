package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.item.ItemGodFood;

public class ModItems {

	public static ItemGodFood itemGodFood;

	public static void preInit() {
		itemGodFood = GimmeStuff.proxy.registerItem(new ItemGodFood());
	}

}
