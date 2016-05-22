package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class Module extends IForgeRegistryEntry.Impl<Module> {

	protected final EnumModuleType type;

	protected Module(String name, EnumModuleType type) {
		setRegistryName(name);
		this.type = type;
	}


	public void update(TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		// NO-OP
	}

	public EnumModuleType getType() {
		return type;
	}
}
