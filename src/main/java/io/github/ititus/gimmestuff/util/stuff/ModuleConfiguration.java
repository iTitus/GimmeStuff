package io.github.ititus.gimmestuff.util.stuff;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

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

	public boolean fill(EnumFacing from, FluidStack resource, boolean doFill, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			if (entry.fill(from, resource, doFill, tile, this)) {
				return true;
			}
		}
		return false;
	}

	public boolean drain(EnumFacing from, FluidStack resource, boolean doDrain, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			if (entry.drain(from, resource, doDrain, tile, this)) {
				return true;
			}
		}
		return false;
	}

	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			FluidStack fluidStack = entry.drain(from, maxDrain, doDrain, tile, this);
			if (fluidStack != null) {
				return fluidStack;
			}
		}
		return null;
	}

	public boolean canFill(EnumFacing from, Fluid fluid, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			if (entry.canFill(from, fluid, tile, this)) {
				return true;
			}
		}
		return false;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			if (entry.canDrain(from, fluid, tile, this)) {
				return true;
			}
		}
		return false;
	}

	public void addTankInfo(EnumFacing from, List<FluidTankInfo> list, TileInfiniteStuff tile) {
		for (ModuleConfigurationEntry entry : entries) {
			entry.addTankInfo(from, list, tile, this);
		}
	}

}
