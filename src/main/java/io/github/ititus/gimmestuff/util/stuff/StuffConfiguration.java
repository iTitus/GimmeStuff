package io.github.ititus.gimmestuff.util.stuff;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class StuffConfiguration implements INBTSerializable<NBTTagCompound> {

	private final List<StuffConfigurationEntry> entries;

	public StuffConfiguration() {
		this.entries = Lists.newArrayList();
	}

	public List<StuffConfigurationEntry> getEntries() {
		return entries;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		NBTTagList tagList = new NBTTagList();
		for (StuffConfigurationEntry entry : entries) {
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
			StuffConfigurationEntry entry = new StuffConfigurationEntry();
			entry.deserializeNBT(tagList.getCompoundTagAt(i));
			entries.add(entry);
		}
	}
}
