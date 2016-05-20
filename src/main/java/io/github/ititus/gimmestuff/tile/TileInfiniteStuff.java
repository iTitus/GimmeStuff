package io.github.ititus.gimmestuff.tile;

import io.github.ititus.gimmestuff.util.stuff.StuffConfiguration;

import net.minecraft.nbt.NBTTagCompound;

public class TileInfiniteStuff extends TileBase {

	private final StuffConfiguration configuration;

	public TileInfiniteStuff() {
		this.configuration = new StuffConfiguration();
	}

	public StuffConfiguration getConfiguration() {
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
}
