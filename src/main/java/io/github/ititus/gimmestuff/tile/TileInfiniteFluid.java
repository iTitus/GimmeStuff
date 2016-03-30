package io.github.ititus.gimmestuff.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileInfiniteFluid extends TileBase implements IFluidHandler {

	private static final int CAPACITY = 1024 * FluidContainerRegistry.BUCKET_VOLUME;

	private FluidStack fluidStack;

	public TileInfiniteFluid() {
	}

	public TileInfiniteFluid(FluidStack fluidStack) {
		if (fluidStack != null) {
			this.fluidStack = new FluidStack(fluidStack, CAPACITY / 2);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid"));
		if (fluidStack != null) {
			this.fluidStack = new FluidStack(fluidStack, CAPACITY / 2);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		if (fluidStack != null) {
			fluidStack.writeToNBT(compound.getCompoundTag("Fluid"));
		}
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		if (resource == null || !resource.isFluidEqual(fluidStack)) {
			return 0;
		}
		return resource.amount;
	}

	@Override
	public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(fluidStack)) {
			return null;
		}
		return new FluidStack(fluidStack, resource.amount);
	}

	@Override
	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
		return new FluidStack(fluidStack, maxDrain);
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from) {
		return new FluidTankInfo[]{new FluidTankInfo(fluidStack, CAPACITY)};
	}
}
