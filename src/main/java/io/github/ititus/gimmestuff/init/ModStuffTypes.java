package io.github.ititus.gimmestuff.init;

import io.github.ititus.gimmestuff.util.stuff.StuffTypeFluidProviderPassive;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModStuffTypes {

	public static StuffTypeFluidProviderPassive stuffTypeFluidProviderPassive;

	public static void init() {
		GameRegistry.register(stuffTypeFluidProviderPassive = new StuffTypeFluidProviderPassive());
	}

}
