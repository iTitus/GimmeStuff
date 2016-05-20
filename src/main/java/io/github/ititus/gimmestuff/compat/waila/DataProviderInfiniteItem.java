package io.github.ititus.gimmestuff.compat.waila;

//import mcp.mobius.waila.api.IWailaConfigHandler;
//import mcp.mobius.waila.api.IWailaDataAccessor;
//import mcp.mobius.waila.api.IWailaDataProvider;

public class DataProviderInfiniteItem /*implements IWailaDataProvider*/ {

	/*
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return ModBlocks.blockInfiniteItem.getPickBlock(accessor.getBlockState(), accessor.getMOP(), accessor.getWorld(), accessor.getPosition(), accessor.getPlayer());
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntity tile = accessor.getTileEntity();
		if (tile instanceof TileInfiniteItem) {
			ItemStack[] stacks = ((TileInfiniteItem) tile).getStacks();
			int count = Utils.countNonNull(stacks);

			if (count == 0) {
				currenttip.add(I18n.format("text.gimmestuff:empty"));
			} else if (count == 1) {
				for (int i = 0; i < stacks.length; i++) {
					ItemStack stack = stacks[i];
					if (stack != null) {
						EnumRarity rarity = stack.getRarity();
						currenttip.add(I18n.format("text.gimmestuff:item", (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + stack.getDisplayName()));


						//String s = "";
						//s += SpecialChars.getRenderString("waila.stack", "1", String.valueOf(stack.getItem().getRegistryName()), "1", String.valueOf(stack.getItemDamage()));
						//currenttip.add(s);

						break;
					}
				}
			} else {
				currenttip.add(I18n.format("text.gimmestuff:items"));
				for (int i = 0; i < stacks.length; i++) {
					ItemStack stack = stacks[i];
					if (stack != null) {
						EnumRarity rarity = itemStack.getRarity();
						currenttip.add("  - " + (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + (stack.hasDisplayName() ? TextFormatting.ITALIC : "") + stack.getDisplayName());
					}
				}
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
