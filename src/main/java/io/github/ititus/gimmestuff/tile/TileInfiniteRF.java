package io.github.ititus.gimmestuff.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;

public class TileInfiniteRF extends TileBase implements IEnergyReceiver, IEnergyProvider {

	public static final int CAPACITY = Integer.MAX_VALUE;

	private boolean hasEnergy;

	public boolean hasEnergy() {
		return hasEnergy;
	}

	public void setHasEnergy(boolean hasEnergy) {
		boolean b = false;
		if (this.hasEnergy != hasEnergy) {
			this.hasEnergy = hasEnergy;
		}
		if (b && worldObj != null) {
			worldObj.checkLight(pos);
			IBlockState state = worldObj.getBlockState(pos);
			worldObj.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		NBTTagCompound energyTag = compound.getCompoundTag("Energy");
		setHasEnergy(energyTag.getBoolean("hasEnergy"));
	}

	@Override
	public void writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		if (hasEnergy) {
			NBTTagCompound energyTag = new NBTTagCompound();
			energyTag.setBoolean("hasEnergy", hasEnergy);
			compound.setTag("Energy", energyTag);
		}
	}

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
		return CAPACITY / 2;
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
