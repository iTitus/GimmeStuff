package io.github.ititus.gimmestuff.tile;

import io.github.ititus.gimmestuff.util.stuff.StuffProviderConfiguration;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileInfiniteStuff extends TileBase implements ITickable {

	private final StuffProviderConfiguration configuration;

	public TileInfiniteStuff() {
		this.configuration = new StuffProviderConfiguration();
	}

	public StuffProviderConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		configuration.deserializeNBT(compound.getCompoundTag("configuration"));
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		compound.setTag("configuration", configuration.serializeNBT());
		return compound;
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			configuration.update(this);
		}
	}
}
