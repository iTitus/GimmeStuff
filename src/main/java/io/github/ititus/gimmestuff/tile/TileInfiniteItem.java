package io.github.ititus.gimmestuff.tile;

import io.github.ititus.gimmestuff.inventory.ItemHandlerInfiniteSingle;
import io.github.ititus.gimmestuff.util.ItemList;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileInfiniteItem extends TileBase {

	private ItemStack stack;

	private IItemHandler itemHandler;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler != null ? itemHandler : (itemHandler = new ItemHandlerInfiniteSingle(this::getStack)));
		}
		return super.getCapability(capability, facing);
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		boolean b = false;
		if (stack != null) {
			stack = stack.copy();
			stack.stackSize = stack.getMaxStackSize();
			if (!ItemStack.areItemStacksEqual(stack, this.stack)) {
				this.stack = stack;
				b = true;
			}
		} else if (this.stack != null) {
			this.stack = null;
			b = true;
		}
		if (b && worldObj != null) {
			IBlockState state = worldObj.getBlockState(pos);
			worldObj.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	public ItemList getItemList() {
		ItemList.Builder builder = ItemList.builder();
		if (stack != null) {
			builder.add(stack);
		}
		return builder.build();
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		setStack(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item")));
	}

	@Override
	public void writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		if (stack != null) {
			NBTTagCompound itemTag = new NBTTagCompound();
			stack.writeToNBT(itemTag);
			compound.setTag("Item", itemTag);
		}
	}
}
