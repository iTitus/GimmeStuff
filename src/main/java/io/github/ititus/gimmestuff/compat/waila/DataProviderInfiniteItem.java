package io.github.ititus.gimmestuff.compat.waila;

import java.util.List;

import io.github.ititus.gimmestuff.tile.TileInfiniteItem;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

public class DataProviderInfiniteItem implements IWailaDataProvider {

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
		if (tile instanceof TileInfiniteItem) {
			ItemStack stack = ((TileInfiniteItem) tile).getStack();
			if (stack == null) {
				currenttip.add(I18n.translateToLocal("text.gimmestuff:empty"));
			} else {
				EnumRarity rarity = stack.getRarity();
				String s = I18n.translateToLocalFormatted("text.gimmestuff:item", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + stack.getDisplayName());
				currenttip.add(s);

				s = "";
				s += SpecialChars.getRenderString("waila.stack", "1", String.valueOf(stack.getItem().getRegistryName()), "1", String.valueOf(stack.getItemDamage()));
				currenttip.add(s);
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
