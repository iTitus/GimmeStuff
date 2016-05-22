package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class PassiveFluidProvider extends FluidModuleBase {

	protected PassiveFluidProvider(String name) {
		super(name);
	}

	public PassiveFluidProvider() {
		super("fluidProviderPassive");
	}

	@Override
	public boolean drain(EnumFacing from, FluidStack resource, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return matches(resource);
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return matches(fluid);
	}
}
