package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.EnumModuleType;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

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
