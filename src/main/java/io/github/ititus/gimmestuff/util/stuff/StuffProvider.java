package io.github.ititus.gimmestuff.util.stuff;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class StuffProvider extends IForgeRegistryEntry.Impl<StuffProvider> {

	protected final EnumStuffProviderType type;

	protected StuffProvider(String name, EnumStuffProviderType type) {
		setRegistryName(name);
		this.type = type;
	}


	public void update(TileInfiniteStuff tile, StuffProviderConfiguration configuration, StuffProviderConfigurationEntry entry) {
		// NO-OP
	}

	public EnumStuffProviderType getType() {
		return type;
	}
}
