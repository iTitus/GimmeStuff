package io.github.ititus.gimmestuff.block;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;
import io.github.ititus.gimmestuff.util.PropertyItemList;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteItem) {
				ItemStack itemStack = ((TileInfiniteItem) tile).getStack();
				if (itemStack == null) {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:empty");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				} else {
					ITextComponent itemNameComponent = itemStack.getTextComponent();//new TextComponentString(itemStack.getDisplayName());
					EnumRarity rarity = itemStack.getRarity();
					itemNameComponent.getStyle().setColor(rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor);

					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:item", itemNameComponent);
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
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteItem);

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteItem) {
			stack = ItemBlockInfiniteItem.getFilledStack(stack, ((TileInfiniteItem) tile).getStack());
		}

		ret.add(stack);
		return ret;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteItem);

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteItem) {
			stack = ItemBlockInfiniteItem.getFilledStack(stack, ((TileInfiniteItem) tile).getStack());
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
		if (tile instanceof TileInfiniteItem && ((TileInfiniteItem) tile).getStack() != null) {
			return 15;
		}
		return 0;
	}
}
