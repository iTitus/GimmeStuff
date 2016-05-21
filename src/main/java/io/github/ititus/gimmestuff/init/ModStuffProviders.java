package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.util.stuff.StuffProviderFluidActive;
import io.github.ititus.gimmestuff.util.stuff.StuffProviderFluidPassive;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModStuffProviders {

	public static StuffProviderFluidPassive stuffFluidProviderPassive;
	public static StuffProviderFluidActive stuffFluidProviderActive;

	public static void init() {
		GameRegistry.register(stuffFluidProviderPassive = new StuffProviderFluidPassive());
		GameRegistry.register(stuffFluidProviderActive = new StuffProviderFluidActive());
	}

}
