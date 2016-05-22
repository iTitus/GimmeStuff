package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

public class ActiveFluidProvider extends PassiveFluidProvider {

	public ActiveFluidProvider() {
		super("fluidProviderActive");
	}

	@Override
	public void update(TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		// TODO: Push fluids to adjacent tanks
	}
}
