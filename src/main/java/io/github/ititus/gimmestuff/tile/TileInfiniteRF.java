package io.github.ititus.gimmestuff.tile;

import net.minecraft.util.EnumFacing;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileInfiniteRF extends TileInfinitePower implements IEnergyReceiver, IEnergyProvider {

	public static final int CAPACITY = Integer.MAX_VALUE;

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return hasEnergy ? maxExtract : 0;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return maxReceive;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return hasEnergy ? CAPACITY / 2 : 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return CAPACITY;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

}
