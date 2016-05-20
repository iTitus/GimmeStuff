package io.github.ititus.gimmestuff.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileInfiniteFluid extends TileBase implements IFluidHandler {

	public static final int CAPACITY = 1024 * FluidContainerRegistry.BUCKET_VOLUME;

	private FluidStack fluidStack;

	public FluidStack getFluidStack() {
		return fluidStack;
	}

	public void setFluidStack(FluidStack fluidStack) {
		boolean b = false;
		if (fluidStack != null) {
			fluidStack = new FluidStack(fluidStack, CAPACITY / 2);
			if (!fluidStack.isFluidEqual(this.fluidStack)) {
				this.fluidStack = fluidStack;
				b = true;
			}
		} else if (this.fluidStack != null) {
			this.fluidStack = null;
			b = true;
		}
		if (b && worldObj != null) {
			//IBlockState state = worldObj.getBlockState(pos);
			//worldObj.notifyBlockUpdate(pos, state, state, 8);
			worldObj.checkLight(pos);
		}
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		setFluidStack(FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Fluid")));
	}

	@Override
	public NBTTagCompound writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		if (fluidStack != null) {
			NBTTagCompound fluidTag = new NBTTagCompound();
			fluidStack.writeToNBT(fluidTag);
			compound.setTag("Fluid", fluidTag);
		}
		return compound;
	}

	@Override
	public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
		if (resource == null || (fluidStack != null && !resource.isFluidEqual(fluidStack))) {
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
		return new FluidTankInfo[]{new FluidTankInfo(fluidStack != null ? fluidStack.copy() : null, CAPACITY)};
	}

	public int getLightValue() {
		if (fluidStack != null) {
			return fluidStack.getFluid().getLuminosity(fluidStack);
		}
		return 0;
	}

}
