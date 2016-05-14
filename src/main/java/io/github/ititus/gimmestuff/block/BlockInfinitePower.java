package io.github.ititus.gimmestuff.block;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.item.ItemBlockInfinitePower;
import io.github.ititus.gimmestuff.tile.TileInfinitePower;
import io.github.ititus.gimmestuff.tile.TileInfiniteRF;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
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

public class BlockInfinitePower extends BlockContainerBase {

	public static final PropertyEnum<PowerType> TYPE = PropertyEnum.create("type", PowerType.class);
	public static final IUnlistedProperty<Boolean> HAS_ENERGY = new Properties.PropertyAdapter<>(PropertyBool.create("has_energy"));

	public BlockInfinitePower() {
		super("blockInfinitePower");
		setDefaultState(blockState.getBaseState().withProperty(TYPE, PowerType.RF));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{TYPE}, new IUnlistedProperty[]{HAS_ENERGY});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileInfinitePower) {
				state = ((IExtendedBlockState) state).withProperty(HAS_ENERGY, ((TileInfinitePower) tile).hasEnergy());
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
		return getDefaultState().withProperty(TYPE, PowerType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return state.getValue(TYPE).getTile();
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
			if (tile instanceof TileInfinitePower) {
				if (!((TileInfinitePower) tile).hasEnergy()) {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:noEnergy", state.getValue(TYPE).getReadableName());
					textComponent.getStyle().setColor(TextFormatting.GRAY);

					player.addChatMessage(textComponent);
				} else {
					ITextComponent textComponent = new TextComponentTranslation("text.gimmestuff:energy", state.getValue(TYPE).getReadableName());
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
		ItemStack stack = new ItemStack(ModBlocks.blockInfinitePower, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfinitePower) {
			stack = ItemBlockInfinitePower.getFilledStack(stack, ((TileInfinitePower) tile).hasEnergy());
		}

		ret.add(stack);
		return ret;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		ItemStack stack = new ItemStack(ModBlocks.blockInfinitePower, 1, damageDropped(state));

		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileInfinitePower) {
			stack = ItemBlockInfinitePower.getFilledStack(stack, ((TileInfinitePower) tile).hasEnergy());
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
		if (tile instanceof TileInfinitePower && ((TileInfinitePower) tile).hasEnergy()) {
			return 15;
		}
		return 0;
	}

	public enum PowerType implements IStringSerializable {

		RF(0, "rf", "RF", () -> new TileInfiniteRF(), () -> true);

		public static final PowerType[] VALUES;

		static {
			PowerType[] _values = values();
			VALUES = new PowerType[_values.length];
			for (PowerType type : _values) {
				VALUES[type.getMeta()] = type;
			}
		}

		private final int meta;
		private final String name, readableName;
		private final Supplier<TileInfinitePower> tileSupplier;
		private final BooleanSupplier activatedSupplier;

		PowerType(int meta, String name, String readableName, Supplier<TileInfinitePower> tileSupplier, BooleanSupplier activatedSupplier) {
			this.meta = meta;
			this.name = name;
			this.readableName = readableName;
			this.tileSupplier = tileSupplier;
			this.activatedSupplier = activatedSupplier;
		}

		public static PowerType byMeta(int meta) {
			return VALUES[meta < 0 || meta >= VALUES.length ? 0 : meta];
		}

		@Override
		public String getName() {
			return name;
		}

		public String getReadableName() {
			return readableName;
		}

		public int getMeta() {
			return meta;
		}

		@Override
		public String toString() {
			return name;
		}

		public TileInfinitePower getTile() {
			return tileSupplier.get();
		}

		public boolean isActive() {
			return activatedSupplier.getAsBoolean();
		}
	}

}
