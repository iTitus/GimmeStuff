package io.github.ititus.gimmestuff.util.stuff;

public class StuffProviderFluidPassive extends StuffProvider {

	public StuffProviderFluidPassive() {
		this("fluidProviderPassive");
	}

	protected StuffProviderFluidPassive(String name) {
		super(name, EnumStuffProviderType.FLUID);
	}

}
