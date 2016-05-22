package io.github.ititus.gimmestuff.util.stuff;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class ModuleConfiguration implements INBTSerializable<NBTTagCompound> {

	private final List<ModuleConfigurationEntry> entries;

	public ModuleConfiguration() {
		this.entries = Lists.newArrayList();
	}

	public List<ModuleConfigurationEntry> getEntries() {
		return entries;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		NBTTagList tagList = new NBTTagList();
		for (ModuleConfigurationEntry entry : entries) {
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
			ModuleConfigurationEntry entry = new ModuleConfigurationEntry();
			entry.deserializeNBT(tagList.getCompoundTagAt(i));
			entries.add(entry);
		}
	}

	public void update(TileInfiniteStuff tile) {
		entries.forEach(entry -> entry.update(tile, this));
	}
}
