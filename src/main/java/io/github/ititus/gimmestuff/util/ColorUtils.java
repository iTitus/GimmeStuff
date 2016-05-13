package io.github.ititus.gimmestuff.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ColorUtils {

	@SideOnly(Side.CLIENT)
	public enum DefaultItemColor implements IItemColor {

		INSTANCE;

		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			if (stack != null) {
				Item item = stack.getItem();
				if (item instanceof IItemWithColor) {
					return ((IItemWithColor) item).getColor(stack, tintIndex);
				}
			}
			return -1;
		}
	}


	@SideOnly(Side.CLIENT)
	public enum DefaultBlockColor implements IBlockColor {

		INSTANCE;

		@Override
		public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
			if (state != null && world != null && pos != null) {
				Block block = state.getBlock();
				if (block instanceof IBlockWithColor) {
					return ((IBlockWithColor) block).getColor(state, world, pos, tintIndex);
				}
			}
			return -1;
		}
	}

	public interface IItemWithColor {

		int getColor(ItemStack stack, int tintIndex);

	}

	public interface IBlockWithColor {

		int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex);

	}


}
