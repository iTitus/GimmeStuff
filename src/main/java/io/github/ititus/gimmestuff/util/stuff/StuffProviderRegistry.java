package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

public final class StuffProviderRegistry {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(GimmeStuff.MOD_ID, "stuffProvider");

	private static final StuffProviderRegistry INSTANCE = new StuffProviderRegistry();

	private final FMLControlledNamespacedRegistry<StuffProvider> stuffProviderRegistry;

	private StuffProviderRegistry() {
		this.stuffProviderRegistry = PersistentRegistryManager.createRegistry(StuffProviderRegistry.REGISTRY_NAME, StuffProvider.class, null, 0, Byte.MAX_VALUE, false, null, null, null);
	}

	public static FMLControlledNamespacedRegistry<StuffProvider> getStuffProviderRegistry() {
		return INSTANCE.stuffProviderRegistry;
	}

}
