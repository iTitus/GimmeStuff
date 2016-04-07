package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTab {

	public static final CreativeTabs MAIN_TAB = new CreativeTabs(GimmeStuff.MOD_ID + ":main.name") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.blockInfiniteFluid);
		}
	};

}
