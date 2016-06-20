package io.github.ititus.gimmestuff.compat.waila;

import java.util.List;

import io.github.ititus.gimmestuff.tile.TileInfiniteFluid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

public class DataProviderInfiniteFluid implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		TileEntity tile = accessor.getTileEntity();
		if (tile instanceof TileInfiniteFluid) {
			FluidStack fluidStack = ((TileInfiniteFluid) tile).getFluidStack();
			if (fluidStack == null) {
				currenttip.add(I18n.format("text.gimmestuff:empty"));
			} else {
				EnumRarity rarity = fluidStack.getFluid().getRarity(fluidStack);
				currenttip.add(I18n.format("text.gimmestuff:fluid", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + fluidStack.getLocalizedName()));
			}
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return null;
	}

}
