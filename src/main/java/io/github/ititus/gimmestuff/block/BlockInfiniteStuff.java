package io.github.ititus.gimmestuff.block;

import javax.annotation.Nullable;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.lib.GuiIDs;
import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockInfiniteStuff extends BlockContainerBase {


	public BlockInfiniteStuff() {
		super("blockInfiniteStuff");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileInfiniteStuff();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && !player.isSneaking()) {
			player.openGui(GimmeStuff.instance, GuiIDs.INFINITE_STUFF, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
