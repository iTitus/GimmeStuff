package io.github.ititus.gimmestuff.util.stuff;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class StuffProviderConfiguration implements INBTSerializable<NBTTagCompound> {

	private final List<StuffProviderConfigurationEntry> entries;

	public StuffProviderConfiguration() {
		this.entries = Lists.newArrayList();
	}

	public List<StuffProviderConfigurationEntry> getEntries() {
		return entries;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		NBTTagList tagList = new NBTTagList();
		for (StuffProviderConfigurationEntry entry : entries) {
			tagList.appendTag(entry.serializeNBT());
		}
		compound.setTag("entries", tagList);

		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		entries.clear();
		NBTTagList tagList = compound.getTagList("entries", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			StuffProviderConfigurationEntry entry = new StuffProviderConfigurationEntry();
			entry.deserializeNBT(tagList.getCompoundTagAt(i));
			entries.add(entry);
		}
	}

	public void update(TileInfiniteStuff tile) {
		entries.forEach(entry -> entry.update(tile, this));
	}
}
