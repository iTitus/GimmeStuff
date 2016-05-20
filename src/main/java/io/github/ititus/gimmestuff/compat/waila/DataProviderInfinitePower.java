package io.github.ititus.gimmestuff.compat.waila;

import java.util.List;

import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.tile.TileInfinitePower;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

//import mcp.mobius.waila.api.IWailaConfigHandler;
//import mcp.mobius.waila.api.IWailaDataAccessor;
//import mcp.mobius.waila.api.IWailaDataProvider;

public class DataProviderInfinitePower /*implements IWailaDataProvider*/ {

	/*
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return ModBlocks.blockInfinitePower.getPickBlock(accessor.getBlockState(), accessor.getMOP(), accessor.getWorld(), accessor.getPosition(), accessor.getPlayer());
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		TileEntity tile = accessor.getTileEntity();
		if (tile instanceof TileInfinitePower) {
			if (!((TileInfinitePower) tile).hasEnergy()) {
				currenttip.add(I18n.format("text.gimmestuff:empty"));
			} else {
				currenttip.add(I18n.format("text.gimmestuff:energy"));
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
	*/
}
