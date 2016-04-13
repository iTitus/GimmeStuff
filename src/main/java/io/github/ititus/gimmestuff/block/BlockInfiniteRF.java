package io.github.ititus.gimmestuff.block;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteRF;
import io.github.ititus.gimmestuff.tile.TileInfiniteRF;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInfiniteRF extends BlockContainerBase {

	public static final IUnlistedProperty<Boolean> HAS_ENERGY = new Properties.PropertyAdapter<>(PropertyBool.create("has_energy"));

	public BlockInfiniteRF() {
		super("blockInfiniteRF");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{HAS_ENERGY});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteRF) {
				state = ((IExtendedBlockState) state).withProperty(HAS_ENERGY, ((TileInfiniteRF) tile).hasEnergy());
			}
		}
		return state;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileInfiniteRF();
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

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteRF) {
				if (!((TileInfiniteRF) tile).hasEnergy()) {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:empty");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				} else {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:energy");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				}
			}
		}
		return true;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		onBlockDestroyedByPlayer(world, pos, state);
		if (willHarvest) {
			harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
		}
		world.setBlockToAir(pos);
		return false;
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		List<ItemStack> ret = Lists.newArrayList();
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteRF);

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteRF) {
			stack = ItemBlockInfiniteRF.getFilledStack(stack, ((TileInfiniteRF) tile).hasEnergy());
		}

		ret.add(stack);
		return ret;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteRF);

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteRF) {
			stack = ItemBlockInfiniteRF.getFilledStack(stack, ((TileInfiniteRF) tile).hasEnergy());
		}

		return stack;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteRF && ((TileInfiniteRF) tile).hasEnergy()) {
			return 15;
		}
		return 0;
	}

}
