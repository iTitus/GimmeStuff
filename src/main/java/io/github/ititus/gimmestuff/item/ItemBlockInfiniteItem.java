package io.github.ititus.gimmestuff.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockInfiniteItem extends ItemBlock {

	public ItemBlockInfiniteItem(Block block) {
		super(block);
		setHasSubtypes(true);
	}
}
