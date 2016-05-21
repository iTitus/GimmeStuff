package io.github.ititus.gimmestuff.util.stuff;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.INBTSerializable;

public class StuffProviderConfigurationEntry implements INBTSerializable<NBTTagCompound> {

	private final Set<EnumFacing> sides;
	private StuffProvider provider;

	public StuffProviderConfigurationEntry() {
		this(null);
	}

	public StuffProviderConfigurationEntry(StuffProvider provider, Collection<EnumFacing> sides) {
		this.provider = provider;
		this.sides = sides != null && sides.size() > 0 ? EnumSet.copyOf(sides) : EnumSet.noneOf(EnumFacing.class);
	}

	public StuffProviderConfigurationEntry(StuffProvider provider, EnumFacing... sides) {
		this.provider = provider;
		this.sides = sides != null && sides.length > 0 ? EnumSet.copyOf(Arrays.asList(sides)) : EnumSet.noneOf(EnumFacing.class);
	}

	public Set<EnumFacing> getSides() {
		return sides;
	}

	public StuffProvider getProvider() {
		return provider;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		if (provider != null) {
			compound.setString("provider", provider.getRegistryName().toString());
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
		provider = StuffProviderRegistry.getStuffProviderRegistry().getValue(new ResourceLocation(compound.getString("provider")));

		sides.clear();
		int[] array = compound.getIntArray("sides");
		for (int side : array) {
			sides.add(EnumFacing.getFront(side));
		}
	}

	public void update(TileInfiniteStuff tile, StuffProviderConfiguration configuration) {
		if (provider != null) {
			provider.update(tile, configuration, this);
		}
	}
}
