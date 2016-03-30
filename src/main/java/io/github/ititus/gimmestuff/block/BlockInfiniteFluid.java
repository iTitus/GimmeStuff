package io.github.ititus.gimmestuff.block;

import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInfiniteFluid extends BlockBase {

	public BlockInfiniteFluid() {
		super("blockInfiniteFluid");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInfiniteFluid();
	}
}
