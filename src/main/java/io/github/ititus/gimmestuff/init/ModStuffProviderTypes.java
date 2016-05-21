package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.util.stuff.StuffProviderFluidActive;
import io.github.ititus.gimmestuff.util.stuff.StuffProviderFluidPassive;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModStuffProviderTypes {

	public static StuffProviderFluidPassive stuffTypeFluidProviderPassive;
	public static StuffProviderFluidActive stuffTypeFluidProviderActive;

	public static void init() {
		GameRegistry.register(stuffTypeFluidProviderPassive = new StuffProviderFluidPassive());
		GameRegistry.register(stuffTypeFluidProviderActive = new StuffProviderFluidActive());
	}

}
