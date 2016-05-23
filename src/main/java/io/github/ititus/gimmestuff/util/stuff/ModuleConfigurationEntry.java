package io.github.ititus.gimmestuff.util.stuff;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import io.github.ititus.gimmestuff.tile.TileInfiniteStuff;
import io.github.ititus.gimmestuff.util.stuff.module.Module;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ModuleConfigurationEntry implements INBTSerializable<NBTTagCompound> {

	public static final int FLUID_CAPACITY = 1024 * FluidContainerRegistry.BUCKET_VOLUME;

	private final Set<EnumFacing> sides;
	private final List<FluidStack> fluidFilter;
	private final List<ItemStack> itemFilter;
	private Module module;
	private boolean whitelist, enabled;

	public ModuleConfigurationEntry() {
		this(null);
	}

	public ModuleConfigurationEntry(Module module, EnumFacing... sides) {
		this(module, sides != null ? Arrays.asList(sides) : Collections.emptyList());
	}

	public ModuleConfigurationEntry(Module module, Collection<EnumFacing> sides) {
		this.module = module;
		this.sides = sides != null && sides.size() > 0 ? EnumSet.copyOf(sides) : EnumSet.noneOf(EnumFacing.class);
		this.enabled = true;
		this.whitelist = true;
		this.fluidFilter = Lists.newArrayList();
		this.itemFilter = Lists.newArrayList();
	}

	public Set<EnumFacing> getSides() {
		return sides;
	}

	public Module getModule() {
		return module;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isWhitelist() {
		return whitelist || (!whitelist && !canBlacklist());
	}

	public boolean isBlacklist() {
		return !whitelist && canBlacklist();
	}

	public boolean canBlacklist() {
		return module != null && module.canBlacklist();
	}

	public EnumModuleType getType() {
		return module != null ? module.getType() : null;
	}

	public List<FluidStack> getFluidFilter() {
		return fluidFilter;
	}

	public List<ItemStack> getItemFilter() {
		return itemFilter;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound compound = new NBTTagCompound();

		if (module != null) {
			compound.setString("module", module.getRegistryName().toString());
		}

		int[] array = new int[sides.size()];
		int i = 0;
		for (EnumFacing facing : sides) {
			array[i++] = facing.getIndex();
		}
		compound.setIntArray("sides", array);

		compound.setBoolean("enabled", enabled);

		compound.setBoolean("whitelist", whitelist);

		compound.setInteger("itemFilterSize", itemFilter.size());
		NBTTagList tagList = new NBTTagList();
		for (int j = 0; j < itemFilter.size(); j++) {
			ItemStack itemStack = itemFilter.get(j);
			if (itemStack != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				itemStack.writeToNBT(nbt);
				nbt.setInteger("slot", j);
				tagList.appendTag(nbt);
			}
		}
		compound.setTag("itemFilter", tagList);

		compound.setInteger("fluidFilterSize", fluidFilter.size());
		tagList = new NBTTagList();
		for (int j = 0; j < fluidFilter.size(); j++) {
			FluidStack fluidStack = fluidFilter.get(j);
			if (fluidStack != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				fluidStack.writeToNBT(nbt);
				nbt.setInteger("slot", j);
				tagList.appendTag(nbt);
			}
		}
		compound.setTag("fluidFilter", tagList);

		return compound;
	}

	@Override
	public void deserializeNBT(NBTTagCompound compound) {
		module = ModuleRegistry.getModuleRegistry().getValue(new ResourceLocation(compound.getString("module")));

		sides.clear();
		int[] array = compound.getIntArray("sides");
		for (int side : array) {
			sides.add(EnumFacing.getFront(side));
		}

		enabled = compound.getBoolean("enabled");

		whitelist = compound.getBoolean("whitelist");

		itemFilter.clear();
		ItemStack[] itemStacks = new ItemStack[compound.getInteger("itemFilterSize")];
		NBTTagList tagList = compound.getTagList("itemFilter", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			ItemStack itemStack = ItemStack.loadItemStackFromNBT(nbt);
			if (itemStack != null) {
				itemStacks[nbt.getInteger("slot")] = itemStack;
			}
		}
		itemFilter.addAll(Arrays.asList(itemStacks));

		fluidFilter.clear();
		FluidStack[] fluidStacks = new FluidStack[compound.getInteger("fluidFilterSize")];
		tagList = compound.getTagList("fluidFilter", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound nbt = tagList.getCompoundTagAt(i);
			FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(nbt);
			if (fluidStack != null) {
				fluidStacks[nbt.getInteger("slot")] = fluidStack;
			}
		}
		fluidFilter.addAll(Arrays.asList(fluidStacks));

	}

	public void update(TileInfiniteStuff tile, ModuleConfiguration configuration) {
		if (module != null && isEnabled()) {
			module.update(tile, configuration, this);
		}
	}

	public boolean matches(FluidStack fluidStack, boolean ignoreNBT) {
		if (fluidStack != null) {
			for (FluidStack filterStack : fluidFilter) {
				if (filterStack != null && filterStack.getFluid() == fluidStack.getFluid() && (ignoreNBT || filterStack.isFluidEqual(fluidStack))) {
					return isWhitelist();
				}
			}
		}
		return isBlacklist();
	}

	public boolean matches(FluidStack fluidStack) {
		return matches(fluidStack, false);
	}

	public boolean matches(Fluid fluid) {
		if (fluid != null) {
			for (FluidStack filterStack : fluidFilter) {
				if (filterStack != null && filterStack.getFluid() == fluid) {
					return isWhitelist();
				}
			}
		}
		return isBlacklist();
	}

	public boolean matches(ItemStack itemStack) {
		if (itemStack != null) {
			for (ItemStack filterStack : itemFilter) {
				if (filterStack != null && filterStack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(filterStack, itemStack)) {
					return isWhitelist();
				}
			}
		}
		return isBlacklist();
	}

	public boolean matches(Item item) {
		if (item != null) {
			for (ItemStack filterStack : itemFilter) {
				if (filterStack != null && filterStack.getItem() == item) {
					return isWhitelist();
				}
			}
		}
		return isBlacklist();
	}

	public boolean fill(EnumFacing from, FluidStack resource, boolean doFill, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		return module != null && isEnabled() && sides.contains(from) && module.fill(from, resource, doFill, tile, configuration, this);
	}

	public boolean drain(EnumFacing from, FluidStack resource, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		return module != null && isEnabled() && sides.contains(from) && module.drain(from, resource, doDrain, tile, configuration, this);
	}

	public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		if (module != null && isEnabled() && sides.contains(from) && !fluidFilter.isEmpty()) {
			FluidStack resource = new FluidStack(fluidFilter.get(0), maxDrain);
			if (drain(from, resource, doDrain, tile, configuration)) {
				return resource;
			}
		}
		return null;
	}

	public boolean canFill(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		return module != null && isEnabled() && sides.contains(from) && module.canFill(from, fluid, tile, configuration, this);
	}

	public boolean canDrain(EnumFacing from, Fluid fluid, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		return module != null && isEnabled() && sides.contains(from) && module.canDrain(from, fluid, tile, configuration, this);
	}

	public void addTankInfo(EnumFacing from, List<FluidTankInfo> list, TileInfiniteStuff tile, ModuleConfiguration configuration) {
		if (module != null && isEnabled() && sides.contains(from)) {
			list.addAll(fluidFilter.stream().filter(fluidStack -> fluidStack != null).map(fluidStack -> new FluidTankInfo(new FluidStack(fluidStack, FLUID_CAPACITY / 2), FLUID_CAPACITY)).collect(Collectors.toList()));
		}
	}
}
