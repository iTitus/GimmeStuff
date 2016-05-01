package io.github.ititus.gimmestuff.tile;

import io.github.ititus.gimmestuff.block.BlockInfiniteItem;
import io.github.ititus.gimmestuff.inventory.ItemHandlerInfiniteMulti;
import io.github.ititus.gimmestuff.inventory.ItemHandlerInfiniteSingle;
import io.github.ititus.gimmestuff.util.IItemSupplier;
import io.github.ititus.gimmestuff.util.ItemList;
import io.github.ititus.gimmestuff.util.Utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TileInfiniteItem extends TileBase implements IItemSupplier {

	private ItemStack[] stacks = new ItemStack[0];

	private IItemHandler itemHandler;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemHandler != null ? itemHandler : (worldObj.getBlockState(pos).getValue(BlockInfiniteItem.TYPE) == BlockInfiniteItem.InfiniteItemType.MULTI ? (itemHandler = new ItemHandlerInfiniteMulti(this)) : (itemHandler = new ItemHandlerInfiniteSingle(this::getStack))));
		}
		return super.getCapability(capability, facing);
	}

	public ItemStack getStack() {
		return getStack(0);
	}

	public ItemStack[] getStacks() {
		return stacks;
	}

	public ItemStack getStack(int index) {
		return index < 0 || index >= stacks.length ? null : stacks[index];
	}

	public void setStack(int index, ItemStack stack) {
		if (index < 0 || index >= stacks.length) {
			return;
		}
		boolean b = false;
		if (stack != null) {
			stack = stack.copy();
			stack.stackSize = stack.getMaxStackSize();
			if (!ItemStack.areItemStacksEqual(stack, getStack(index))) {
				stacks[index] = stack;
				b = true;
			}
		} else if (getStack(index) != null) {
			stacks[index] = null;
			b = true;
		}
		if (b && worldObj != null) {
			IBlockState state = worldObj.getBlockState(pos);
			worldObj.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	public ItemList getItemList() {
		ItemList.Builder builder = ItemList.builder();
		for (ItemStack stack : stacks) {
			if (stack != null) {
				builder.add(stack);
			}
		}
		return builder.build();
	}

	@Override
	public void readFromCustomNBT(NBTTagCompound compound) {
		super.readFromCustomNBT(compound);
		NBTTagCompound itemsTag = compound.getCompoundTag("Items");
		stacks = new ItemStack[itemsTag.getInteger("size")];
		NBTTagList itemList = itemsTag.getTagList("ItemList", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < itemList.tagCount(); i++) {
			NBTTagCompound tag = itemList.getCompoundTagAt(i);

			int index = tag.getInteger("index");
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);

			if (stack != null) {
				stack.stackSize = stack.getMaxStackSize();
				setStack(index, stack);
			}
		}
	}

	@Override
	public void writeToCustomNBT(NBTTagCompound compound) {
		super.writeToCustomNBT(compound);
		NBTTagCompound itemsTag = new NBTTagCompound();
		itemsTag.setInteger("size", stacks.length);
		NBTTagList itemList = new NBTTagList();
		for (int i = 0; i < stacks.length; i++) {
			ItemStack stack = stacks[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("index", i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		itemsTag.setTag("ItemList", itemList);
		compound.setTag("Items", itemsTag);
	}

	@Override
	public int getItemStackCount() {
		return stacks.length;
	}

	@Override
	public ItemStack getItemStack(int index) {
		if (index < 0 || index >= stacks.length) {
			return null;
		}
		return stacks[index];
	}

	public boolean isEmpty() {
		return Utils.containsOnlyNull(stacks);
	}
}
