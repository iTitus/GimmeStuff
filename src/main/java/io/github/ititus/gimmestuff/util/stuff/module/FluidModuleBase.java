package io.github.ititus.gimmestuff.util.stuff.module;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.EnumModuleType;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public abstract class FluidModuleBase extends Module {

	public static final int CAPACITY = 1024 * FluidContainerRegistry.BUCKET_VOLUME;

	protected final List<FluidStack> filter;
	protected boolean whitelist = true;

	protected FluidModuleBase(String name) {
		super(name, EnumModuleType.FLUID);
		filter = Lists.newArrayList();
	}

	public List<FluidStack> getFilter() {
		return filter;
	}

	public boolean isWhitelist() {
		return whitelist;
	}

	public boolean isBlacklist() {
		return !whitelist;
	}

	public boolean matches(FluidStack fluidStack, boolean ignoreNBT) {
		if (fluidStack != null) {
			for (FluidStack filterStack : filter) {
				if (filterStack != null && filterStack.getFluid() == fluidStack.getFluid() && (ignoreNBT || filterStack.isFluidEqual(fluidStack))) {
					return whitelist;
				}
			}
		}
		return !whitelist;
	}

	public boolean matches(FluidStack fluidStack) {
		return matches(fluidStack, false);
	}

	public boolean matches(Fluid fluid) {
		if (fluid != null) {
			for (FluidStack filterStack : filter) {
				if (filterStack != null && filterStack.getFluid() == fluid) {
					return whitelist;
				}
			}
		}
		return !whitelist;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		if (!filter.isEmpty()) {
			FluidStack resource = new FluidStack(filter.get(0), maxDrain);
			if (drain(from, resource, doDrain, tile, configuration, entry)) {
				return resource;
			}
		}
		return null;
	}

	@Override
	public void addTankInfo(EnumFacing from, List<FluidTankInfo> list, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		list.addAll(filter.stream().filter(fluidStack -> fluidStack != null).map(fluidStack -> new FluidTankInfo(new FluidStack(fluidStack, CAPACITY / 2), CAPACITY)).collect(Collectors.toList()));
	}
}
