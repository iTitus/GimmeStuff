package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ModCreativeTab {

	public static final CreativeTabs MAIN_TAB = new CreativeTabs(GimmeStuff.MOD_ID + ":main.name") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.blockInfiniteFluid);
		}
	};

}
