package io.github.ititus.gimmestuff.block;

import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;
import io.github.ititus.gimmestuff.util.PropertyItemList;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockInfiniteItem extends BlockContainerBase {

	public static final PropertyItemList ITEM = new PropertyItemList("item");

	public BlockInfiniteItem() {
		super("blockInfiniteItem");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{ITEM});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteFluid) {
				state = ((IExtendedBlockState) state).withProperty(ITEM, ((TileInfiniteItem) tile).getItemList());
			}
		}
		return state;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileInfiniteItem();
	}
}
