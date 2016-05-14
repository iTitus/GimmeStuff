package io.github.ititus.gimmestuff.block;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteItem;
import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;
import io.github.ititus.gimmestuff.util.PropertyItemList;
import io.github.ititus.gimmestuff.util.Utils;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
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

public class BlockInfiniteItem extends BlockContainerBase {

	public static final PropertyItemList ITEM_LIST = new PropertyItemList("item_list");
	public static final PropertyEnum<InfiniteItemType> TYPE = PropertyEnum.create("type", InfiniteItemType.class);

	public BlockInfiniteItem() {
		super("blockInfiniteItem");
		setDefaultState(blockState.getBaseState().withProperty(TYPE, InfiniteItemType.SINGLE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{TYPE}, new IUnlistedProperty[]{ITEM_LIST});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfiniteFluid) {
				state = ((IExtendedBlockState) state).withProperty(ITEM_LIST, ((TileInfiniteItem) tile).getItemList());
			}
		}
		return state;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, InfiniteItemType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
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
				ItemStack[] stacks = ((TileInfiniteItem) tile).getStacks();
				int count = Utils.countNonNull(stacks);

				if (count == 0) {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:empty");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				} else if (count == 1) {
					for (int i = 0; i < stacks.length; i++) {
						ItemStack itemStack = stacks[i];
						if (itemStack != null) {
							ITextComponent itemNameComponent = itemStack.getTextComponent();
							EnumRarity rarity = itemStack.getRarity();
							itemNameComponent.getStyle().setColor(rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor);

							ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:item", itemNameComponent);
							textComponent.getStyle().setColor(TextFormatting.GRAY);

							player.addChatMessage(textComponent);
							break;
						}
					}
				} else {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:items");
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);

					for (int i = 0; i < stacks.length; i++) {
						ItemStack itemStack = stacks[i];
						if (itemStack != null) {
							ITextComponent stringComponent = new TextComponentString("  - ");
							stringComponent.getStyle().setColor(TextFormatting.GRAY);

							ITextComponent itemNameComponent = itemStack.getTextComponent();
							EnumRarity rarity = itemStack.getRarity();
							itemNameComponent.getStyle().setColor(rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor);

							stringComponent.appendSibling(itemNameComponent);

							player.addChatMessage(stringComponent);
						}
					}
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
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteItem, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteItem) {
			stack = ItemBlockInfiniteItem.getFilledStack(stack, ((TileInfiniteItem) tile).getStacks());
		}

		ret.add(stack);
		return ret;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(ModBlocks.blockInfiniteItem, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfiniteItem) {
			stack = ItemBlockInfiniteItem.getFilledStack(stack, ((TileInfiniteItem) tile).getStacks());
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
		if (tile instanceof TileInfiniteItem && !((TileInfiniteItem) tile).isEmpty()) {
			return 15;
		}
		return 0;
	}

	public enum InfiniteItemType implements IStringSerializable {

		SINGLE(0, "single"), MULTI(1, "multi");

		public static final InfiniteItemType[] VALUES;

		static {
			InfiniteItemType[] values = values();
			VALUES = new InfiniteItemType[values.length];
			for (InfiniteItemType type : values) {
				VALUES[type.meta] = type;
			}
		}

		private final int meta;
		private final String name;

		InfiniteItemType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public static InfiniteItemType byMeta(int meta) {
			return VALUES[meta < 0 || meta >= VALUES.length ? 0 : meta];
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return getName();
		}

		public int getMeta() {
			return meta;
		}
	}
}
