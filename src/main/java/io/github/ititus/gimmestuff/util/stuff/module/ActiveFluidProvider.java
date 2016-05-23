package io.github.ititus.gimmestuff.util.stuff.module;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfigurationEntry;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class ActiveFluidProvider extends PassiveFluidProvider {

	public ActiveFluidProvider() {
		super("fluidProviderActive");
	}

	@Override
	public void update(TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry) {
		TileEntity[] neighborTiles = tile.getNeighborTiles();
		for (EnumFacing facing : EnumFacing.VALUES) {
			TileEntity offsetTile = neighborTiles[facing.getIndex()];
			if (offsetTile instanceof IFluidHandler) {
				IFluidHandler fluidHandler = (IFluidHandler) offsetTile;
				pushFluid(tile, configuration, entry, fluidHandler, facing.getOpposite());
			}
		}
	}

	private void pushFluid(TileInfiniteStuff tile, ModuleConfiguration configuration, ModuleConfigurationEntry entry, IFluidHandler fluidHandler, EnumFacing from) {
		entry.getFluidFilter().stream().filter(filterStack -> fluidHandler.canFill(from, filterStack.getFluid())).forEach(filterStack -> fluidHandler.fill(from, new FluidStack(filterStack, ModuleConfigurationEntry.FLUID_CAPACITY / 2), true));
	}
}
