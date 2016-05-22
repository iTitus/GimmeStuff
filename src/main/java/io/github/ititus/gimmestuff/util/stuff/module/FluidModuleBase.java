package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.util.stuff.EnumModuleType;
import io.github.ititus.gimmestuff.util.stuff.Module;

public abstract class FluidModuleBase extends Module {

	protected FluidModuleBase(String name) {
		super(name, EnumModuleType.FLUID);
	}

}
