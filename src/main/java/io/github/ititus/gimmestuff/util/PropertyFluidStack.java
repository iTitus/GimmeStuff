package io.github.ititus.gimmestuff.util;

import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidStack;

public class PropertyFluidStack implements IUnlistedProperty<FluidStack> {

	private final String name;

	public PropertyFluidStack(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(FluidStack value) {
		return value == null || value.getFluid() != null;
	}

	@Override
	public Class<FluidStack> getType() {
		return FluidStack.class;
	}

	@Override
	public String valueToString(FluidStack value) {
		if (value == null) {
			return "null";
		}
		return "[fluid=" + value.getFluid().getName() + ", amount=" + value.amount + (value.tag != null ? ", tag=" + value.tag : "") + "]";
	}
}
