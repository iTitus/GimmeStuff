package io.github.ititus.gimmestuff.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockBase implements ITileEntityProvider {

	public BlockContainerBase(String name) {
		super(name);
		this.isBlockContainer = true;
	}

	@Override
	public final TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

	@Override
	public abstract TileEntity createTileEntity(World world, IBlockState state);

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	public boolean onBlockEventReceived(World world, BlockPos pos, IBlockState state, int eventID, int eventParam) {
		super.onBlockEventReceived(world, pos, state, eventID, eventParam);
		TileEntity tile = world.getTileEntity(pos);
		return tile != null && tile.receiveClientEvent(eventID, eventParam);
	}
}
