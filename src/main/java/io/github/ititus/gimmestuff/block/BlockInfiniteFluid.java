package io.github.ititus.gimmestuff.block;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.util.ColorUtils;
import io.github.ititus.gimmestuff.util.PropertyFluidStack;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInfiniteFluid extends BlockContainerBase implements ColorUtils.IBlockWithColor {

	public static final PropertyFluidStack FLUID = new PropertyFluidStack("fluid");

	public BlockInfiniteFluid() {
		super("blockInfiniteFluid");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[]{FLUID});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteFluid) {
				state = ((IExtendedBlockState) state).withProperty(FLUID, ((TileInfiniteFluid) tile).getFluidStack());
			}
		}
		return state;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileInfiniteFluid();
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteFluid) {
			return ((TileInfiniteFluid) tile).getLightValue();
		}
		return 0;
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
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteFluid) {
			//TODO: Fix this for creative players
			if (FluidUtil.interactWithTank(heldItem, player, (IFluidHandler) tile, side)) {
				return true;
			}
			if (world.isRemote) {
				FluidStack fluidStack = ((TileInfiniteFluid) tile).getFluidStack();
				if (fluidStack == null) {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:empty");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				} else {
					ITextComponent fluidNameComponent = new TextComponentString(fluidStack.getLocalizedName());
					EnumRarity rarity = fluidStack.getFluid().getRarity(fluidStack);
					fluidNameComponent.getStyle().setColor(rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor);

					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:fluid", fluidNameComponent);
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				}
				return true;
			}
		}
		return false;
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
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteFluid, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteFluid) {
			stack = ItemBlockInfiniteFluid.getFilledStack(stack, ((TileInfiniteFluid) tile).getFluidStack());
		}

		ret.add(stack);
		return ret;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteFluid, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteFluid) {
			stack = ItemBlockInfiniteFluid.getFilledStack(stack, ((TileInfiniteFluid) tile).getFluidStack());
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
		if (tile instanceof TileInfiniteFluid && ((TileInfiniteFluid) tile).getFluidStack() != null) {
			return 15;
		}
		return 0;
	}

	@Override
	public int getColor(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
		if (state instanceof IExtendedBlockState) {
			FluidStack fluidStack = ((IExtendedBlockState) state).getValue(BlockInfiniteFluid.FLUID);
			if (fluidStack != null) {
				return fluidStack.getFluid().getColor(fluidStack);
			}
		}
		return -1;
	}
}
