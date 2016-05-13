package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.block.BlockInfinitePower;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteFluid;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;
import io.github.ititus.gimmestuff.item.ItemBlockInfinitePower;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;
import io.github.ititus.gimmestuff.tile.TileInfiniteRF;

public class ModBlocks {

	public static BlockInfiniteItem blockInfiniteItem;
	public static BlockInfiniteFluid blockInfiniteFluid;
	public static BlockInfinitePower blockInfinitePower;

	public static void preInit() {

		blockInfiniteItem = new BlockInfiniteItem();
		GimmeStuff.proxy.registerWithCustomItemBlock(blockInfiniteItem, new ItemBlockInfiniteItem(blockInfiniteItem));
		GimmeStuff.proxy.registerTileEntity(TileInfiniteItem.class, blockInfiniteItem.getName());

		blockInfiniteFluid = new BlockInfiniteFluid();
		GimmeStuff.proxy.registerWithCustomItemBlock(blockInfiniteFluid, new ItemBlockInfiniteFluid(blockInfiniteFluid));
		GimmeStuff.proxy.registerTileEntity(TileInfiniteFluid.class, blockInfiniteFluid.getName());

		blockInfinitePower = new BlockInfinitePower();
		GimmeStuff.proxy.registerWithCustomItemBlock(blockInfinitePower, new ItemBlockInfinitePower(blockInfinitePower));
		GimmeStuff.proxy.registerTileEntity(TileInfiniteRF.class, blockInfinitePower.getName() + ".rf");
	}

}
