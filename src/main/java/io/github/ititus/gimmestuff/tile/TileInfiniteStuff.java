package io.github.ititus.gimmestuff.tile;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.util.Utils;
import io.github.ititus.gimmestuff.util.stuff.ModuleConfiguration;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileInfiniteStuff extends TileBase implements ITickable, IFluidHandler {

	private final TileEntity[] neighborTiles;
	private final ModuleConfiguration configuration;
	private boolean updateNeighbors;

	public TileInfiniteStuff() {
		this.configuration = new ModuleConfiguration();
		this.neighborTiles = new TileEntity[EnumFacing.VALUES.length];
		this.updateNeighbors = true;
	}

	public ModuleConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		configuration.deserializeNBT(compound.getCompoundTag("configuration"));
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		compound.setTag("configuration", configuration.serializeNBT());
		return compound;
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			if (!updateNeighbors) {
				for (TileEntity tile : neighborTiles) {
					if (tile != null && tile.isInvalid()) {
						updateNeighbors = true;
					}
				}
			}
			if (updateNeighbors) {
				doUpdateNeighbors();
			}
			configuration.update(this);
		}
	}

	private void doUpdateNeighbors() {
		if (updateNeighbors && worldObj != null) {
			Utils.clear(neighborTiles);
			for (EnumFacing facing : EnumFacing.VALUES) {
				BlockPos offsetPos = pos.offset(facing);
				IBlockState state = worldObj.getBlockState(offsetPos);
				if (state.getBlock().hasTileEntity(state)) {
					TileEntity offsetTile = worldObj.getTileEntity(offsetPos);
					if (offsetTile != null && !offsetTile.isInvalid()) {
						neighborTiles[facing.getIndex()] = offsetTile;
					}
				}
			}
			updateNeighbors = false;
		}
	}

	public void updateNeighbors() {
		this.updateNeighbors = true;
	}

	public TileEntity[] getNeighborTiles() {
		return neighborTiles;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		return resource != null && configuration.fill(from, resource, doFill, this) ? resource.amount : 0;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		return resource != null && configuration.drain(from, resource, doDrain, this) ? resource : null;
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return configuration.drain(from, maxDrain, doDrain, this);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return configuration.canFill(from, fluid, this);
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return configuration.canDrain(from, fluid, this);
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		List<FluidTankInfo> list = Lists.newArrayList();
		configuration.addTankInfo(from, list, this);
		return list.toArray(new FluidTankInfo[list.size()]);
	}
}
