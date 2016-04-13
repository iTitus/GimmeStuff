package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockBase;
import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteFluid;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;
import io.github.ititus.gimmestuff.tile.TileBase;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static BlockInfiniteItem blockInfiniteItem;
	public static BlockInfiniteFluid blockInfiniteFluid;

	public static void preInit() {

		blockInfiniteItem = new BlockInfiniteItem();
		registerWithCustomItemBlock(blockInfiniteItem, new ItemBlockInfiniteItem(blockInfiniteItem));
		registerTileEntity(TileInfiniteItem.class, blockInfiniteItem.getName());

		blockInfiniteFluid = new BlockInfiniteFluid();
		registerWithCustomItemBlock(blockInfiniteFluid, new ItemBlockInfiniteFluid(blockInfiniteFluid));
		registerTileEntity(TileInfiniteFluid.class, blockInfiniteFluid.getName());
	}

	private static <T extends BlockBase> T registerWithDefaultItemBlock(T block) {
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		return block;
	}

	private static <T extends BlockBase> T registerWithCustomItemBlock(T block, Item itemBlock) {
		GameRegistry.register(block);
		GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
		return block;
	}

	private static void registerTileEntity(Class<? extends TileBase> tileClass, String name) {
		GameRegistry.registerTileEntity(tileClass, "tileentity." + GimmeStuff.MOD_ID + ":" + name);
	}

}
