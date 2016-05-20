package io.github.ititus.gimmestuff.item;

import java.util.List;

import io.github.ititus.gimmestuff.block.BlockInfinitePower;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockInfinitePower extends ItemBlock {

	public ItemBlockInfinitePower(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	public static ItemStack getFilledStack(ItemStack stack, boolean hasEnergy) {
		stack = ItemStack.copyItemStack(stack);
		if (stack != null && hasEnergy) {
			NBTTagCompound compound = new NBTTagCompound();
			NBTTagCompound blockEntityTag = new NBTTagCompound();
			NBTTagCompound energyTag = new NBTTagCompound();

			energyTag.setBoolean("hasEnergy", hasEnergy);

			blockEntityTag.setTag("Energy", energyTag);
			compound.setTag("BlockEntityTag", blockEntityTag);
			stack.setTagCompound(compound);
		}
		return stack;
	}

	public static boolean hasEnergy(ItemStack stack) {
		if (stack != null && stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if (compound.hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound nbt = compound.getCompoundTag("BlockEntityTag");
				if (nbt.hasKey("Energy", Constants.NBT.TAG_COMPOUND)) {
					return nbt.getCompoundTag("Energy").getBoolean("hasEnergy");
				}
			}
		}
		return false;
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName(stack) + "." + BlockInfinitePower.PowerType.byMeta(stack.getMetadata()).getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		if (!hasEnergy(stack)) {
			tooltip.add(I18n.format("text.gimmestuff:empty"));
		} else {
			tooltip.add(I18n.format("text.gimmestuff:energy"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems) {
		for (BlockInfinitePower.PowerType type : BlockInfinitePower.PowerType.VALUES) {
			if (type.isActive()) {
				subItems.add(getFilledStack(new ItemStack(item, 1, type.getMeta()), false));
				subItems.add(getFilledStack(new ItemStack(item, 1, type.getMeta()), true));
			}
		}
	}

}
