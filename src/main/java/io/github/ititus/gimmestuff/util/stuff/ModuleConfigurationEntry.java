package io.github.ititus.gimmestuff.util.stuff;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.module.Module;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.INBTSerializable;

public class ModuleConfigurationEntry implements INBTSerializable<NBTTagCompound> {

	private final Set<EnumFacing> sides;
	private Module module;

	public ModuleConfigurationEntry() {
		this(null);
	}

	public ModuleConfigurationEntry(Module module, Collection<EnumFacing> sides) {
		this.module = module;
		this.sides = sides != null && sides.size() > 0 ? EnumSet.copyOf(sides) : EnumSet.noneOf(EnumFacing.class);
	}

	public ModuleConfigurationEntry(Module module, EnumFacing... sides) {
		this.module = module;
		this.sides = sides != null && sides.length > 0 ? EnumSet.copyOf(Arrays.asList(sides)) : EnumSet.noneOf(EnumFacing.class);
	}

	public Set<EnumFacing> getSides() {
		return sides;
	}

	public Module getModule() {
		return module;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		if (module != null) {
			compound.setString("module", module.getRegistryName().toString());
		}

		int[] array = new int[sides.size()];
		int i = 0;
		for (EnumFacing facing : sides) {
			array[i++] = facing.getIndex();
		}
		compound.setIntArray("sides", array);

		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		module = ModuleRegistry.getModuleRegistry().getValue(new ResourceLocation(compound.getString("module")));

		sides.clear();
		int[] array = compound.getIntArray("sides");
		for (int side : array) {
			sides.add(EnumFacing.getFront(side));
		}
	}

	public void update(TileInfiniteStuff tile, ModuleConfiguration configuration) {
		if (module != null) {
			module.update(tile, configuration, this);
		}
	}
}
