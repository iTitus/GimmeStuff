package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.util.stuff.module.Module;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.PersistentRegistryManager;

public final class ModuleRegistry {

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(GimmeStuff.MOD_ID, "stuffModule");

	private static final ModuleRegistry INSTANCE = new ModuleRegistry();

	private final FMLControlledNamespacedRegistry<Module> moduleRegistry;

	private ModuleRegistry() {
		this.moduleRegistry = PersistentRegistryManager.createRegistry(ModuleRegistry.REGISTRY_NAME, Module.class, null, 0, Byte.MAX_VALUE, false, null, null, null);
	}

	public static FMLControlledNamespacedRegistry<Module> getModuleRegistry() {
		return INSTANCE.moduleRegistry;
	}

}
