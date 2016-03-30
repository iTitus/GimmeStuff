package io.github.ititus.gimmestuff.block;

import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInfiniteFluid extends BlockBase {

	public BlockInfiniteFluid() {
		super("blockInfiniteFluid");
		setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileInfiniteFluid();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

}
