package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

public final class StuffTypeRegistry {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(GimmeStuff.MOD_ID, "stuffType");

	private static final StuffTypeRegistry INSTANCE = new StuffTypeRegistry();

	private final FMLControlledNamespacedRegistry<StuffType> stuffTypeRegistry;

	private StuffTypeRegistry() {
		this.stuffTypeRegistry = PersistentRegistryManager.createRegistry(StuffTypeRegistry.REGISTRY_NAME, StuffType.class, null, 0, Byte.MAX_VALUE, false, null, null, null);
	}

	public static IForgeRegistry<StuffType> getStuffTypeRegistry() {
		return INSTANCE.stuffTypeRegistry;
	}

}
