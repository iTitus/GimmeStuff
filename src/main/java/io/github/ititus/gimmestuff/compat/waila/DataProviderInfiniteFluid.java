package io.github.ititus.gimmestuff.compat.waila;

//import mcp.mobius.waila.api.IWailaConfigHandler;
//import mcp.mobius.waila.api.IWailaDataAccessor;
//import mcp.mobius.waila.api.IWailaDataProvider;

public class DataProviderInfiniteFluid /*implements IWailaDataProvider*/ {

	/*
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

		TileEntity tile = accessor.getTileEntity();
		if (tile instanceof TileInfiniteFluid) {
			FluidStack fluidStack = ((TileInfiniteFluid) tile).getFluidStack();
			if (fluidStack == null) {
				currenttip.add(I18n.format("text.gimmestuff:empty"));
			} else {
				EnumRarity rarity = fluidStack.getFluid().getRarity(fluidStack);
				currenttip.add(I18n.format("text.gimmestuff:fluid", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + fluidStack.getLocalizedName()));
			}
		}

		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return null;
	}
	*/
}
