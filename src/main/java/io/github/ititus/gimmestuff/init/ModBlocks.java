package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static BlockInfiniteFluid blockInfiniteFluid;

	public static void preInit() {
		blockInfiniteFluid = new BlockInfiniteFluid();
		GameRegistry.registerBlock(blockInfiniteFluid);
		GameRegistry.registerTileEntity(TileInfiniteFluid.class, "tileentity." + GimmeStuff.MOD_ID + ":" + blockInfiniteFluid.getName());
	}

}
