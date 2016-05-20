package io.github.ititus.gimmestuff.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileInfinitePower extends TileBase {

	protected boolean hasEnergy;

	public boolean hasEnergy() {
		return hasEnergy;
	}

	public void setHasEnergy(boolean hasEnergy) {
		boolean b = false;
		if (this.hasEnergy != hasEnergy) {
			this.hasEnergy = hasEnergy;
		}
		if (b && worldObj != null) {
			IBlockState state = worldObj.getBlockState(pos);
			worldObj.notifyBlockUpdate(pos, state, state, 8);
		}
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		NBTTagCompound energyTag = compound.getCompoundTag("Energy");
		setHasEnergy(energyTag.getBoolean("hasEnergy"));
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		if (hasEnergy) {
			NBTTagCompound energyTag = new NBTTagCompound();
			energyTag.setBoolean("hasEnergy", hasEnergy);
			compound.setTag("Energy", energyTag);
		}
		return compound;
	}
}
