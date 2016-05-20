package io.github.ititus.gimmestuff.util.stuff;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.INBTSerializable;

public class StuffConfigurationEntry implements INBTSerializable<NBTTagCompound> {

	private final Set<EnumFacing> sides;
	private StuffType type;

	public StuffConfigurationEntry() {
		this(null);
	}

	public StuffConfigurationEntry(StuffType type, Collection<EnumFacing> sides) {
		this.type = type;
		this.sides = sides != null && sides.size() > 0 ? EnumSet.copyOf(sides) : EnumSet.noneOf(EnumFacing.class);
	}

	public StuffConfigurationEntry(StuffType type, EnumFacing... sides) {
		this.type = type;
		this.sides = sides != null && sides.length > 0 ? EnumSet.copyOf(Arrays.asList(sides)) : EnumSet.noneOf(EnumFacing.class);
	}

	public Set<EnumFacing> getSides() {
		return sides;
	}

	public StuffType getType() {
		return type;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		if (type != null) {
			compound.setString("type", type.getRegistryName().toString());
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
		type = StuffTypeRegistry.getStuffTypeRegistry().getValue(new ResourceLocation(compound.getString("type")));

		sides.clear();
		int[] array = compound.getIntArray("sides");
		for (int side : array) {
			sides.add(EnumFacing.getFront(side));
		}
	}
}
