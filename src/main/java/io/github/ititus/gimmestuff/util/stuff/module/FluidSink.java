package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidSink extends FluidModuleBase {

	public FluidSink() {
		super("fluidSink");
		this.canBlacklist = true;
	}

	@Override
	public boolean fill(EnumFacing from, FluidStack resource, boolean doFill, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return entry.matches(resource);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return entry.matches(fluid);
	}
}
