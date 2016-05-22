package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.util.stuff.module.ActiveFluidProvider;
import io.github.ititus.gimmestuff.util.stuff.module.FluidSink;
import io.github.ititus.gimmestuff.util.stuff.module.PassiveFluidProvider;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModModules {

	public static PassiveFluidProvider passiveFluidProvider;
	public static ActiveFluidProvider activeFluidProvider;
	public static FluidSink fluidSink;

	public static void init() {
		GameRegistry.register(passiveFluidProvider = new PassiveFluidProvider());
		GameRegistry.register(activeFluidProvider = new ActiveFluidProvider());
		GameRegistry.register(fluidSink = new FluidSink());
	}

}
