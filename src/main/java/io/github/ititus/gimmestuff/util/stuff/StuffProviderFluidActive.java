package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

public class StuffProviderFluidActive extends StuffProviderFluidPassive {

	public StuffProviderFluidActive() {
		super("fluidProviderActive");
	}

	@Override
	public void update(TileInfiniteStuff tile, StuffProviderConfiguration configuration, StuffProviderConfigurationEntry entry) {
		// TODO: Push fluids to adjacent tanks
	}
}
