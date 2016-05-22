package io.github.ititus.gimmestuff.util.stuff.module;

import java.util.List;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.EnumModuleType;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
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

	public boolean fill(EnumFacing from, FluidStack resource, boolean doFill, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return false;
	}

	public boolean drain(EnumFacing from, FluidStack resource, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return false;
	}

	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return null;
	}

	public boolean canFill(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return false;
	}

	public boolean canDrain(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		return false;
	}

	public void addTankInfo(EnumFacing from, List<FluidTankInfo> list, TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		// NO-OP
	}
}
