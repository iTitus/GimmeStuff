package io.github.ititus.gimmestuff.item;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;
import io.github.ititus.gimmestuff.util.ColorUtils;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfiniteFluid extends ItemBlock implements ColorUtils.IItemWithColor {

	public ItemBlockInfiniteFluid(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	public static ItemStack getFilledStack(ItemStack stack, FluidStack fluid) {
		stack = ItemStack.copyItemStack(stack);
		if (stack != null && fluid != null) {
			FluidStack fluidStack = new FluidStack(fluid, TileInfiniteFluid.CAPACITY / 2);
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagCompound blockEntityTag = new NBTTagCompound();
			NBTTagCompound fluidTag = new NBTTagCompound();

			fluidStack.writeToNBT(fluidTag);

			blockEntityTag.setTag("Fluid", fluidTag);
			compound.setTag("BlockEntityTag", blockEntityTag);
			stack.setTagCompound(compound);
		}
		return stack;
	}

	public static FluidStack getFluidStack(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound nbt = compound.getCompoundTag("BlockEntityTag");
				if (nbt.hasKey("Fluid", Constants.NBT.TAG_COMPOUND)) {
					return FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("Fluid"));
				}
			}
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		FluidStack fluidStack = getFluidStack(stack);
		if (fluidStack == null) {
			tooltip.add(I18n.translateToLocal("text.gimmestuff:empty"));
		} else {
			EnumRarity rarity = fluidStack.getFluid().getRarity(fluidStack);
			tooltip.add(I18n.translateToLocalFormatted("text.gimmestuff:fluid", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + fluidStack.getLocalizedName()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		super.getSubItems(item, tab, subItems);
		List<Map.Entry<String, Fluid>> fluids = Lists.newArrayList(FluidRegistry.getRegisteredFluids().entrySet());
		fluids.sort((fluidEntry1, fluidEntry2) -> String.CASE_INSENSITIVE_ORDER.compare(fluidEntry1 != null ? fluidEntry1.getKey() : "", fluidEntry2 != null ? fluidEntry2.getKey() : ""));
		subItems.addAll(fluids.stream().map(fluidEntry -> getFilledStack(new ItemStack(item), new FluidStack(fluidEntry.getValue(), 0))).collect(Collectors.toList()));
	}

	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		FluidStack fluidStack = getFluidStack(stack);
		if (fluidStack != null) {
			return fluidStack.getFluid().getColor(fluidStack);
		}
		return -1;
	}
}
