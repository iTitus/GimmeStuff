package io.github.ititus.gimmestuff.item;

import java.util.List;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModCreativeTab;
import io.github.ititus.gimmestuff.util.INameable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGodFood extends ItemFood implements INameable {

	private final String name;

	public ItemGodFood() {
		super(20, 1024, false);
		this.name = "itemGodFood";
		setUnlocalizedName(GimmeStuff.MOD_ID + ":" + name);
		setRegistryName(GimmeStuff.MOD_ID, name);
		setCreativeTab(ModCreativeTab.MAIN_TAB);

		setMaxStackSize(1);
		setContainerItem(this);
		setAlwaysEdible();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {

		FoodStats foodStats = player.getFoodStats();
		if (foodStats != null) {
			tooltip.add(I18n.translateToLocalFormatted("text.gimmestuff:foodLevel", foodStats.getFoodLevel()));
			tooltip.add(I18n.translateToLocalFormatted("text.gimmestuff:saturationLevel", foodStats.getSaturationLevel()));
			tooltip.add(I18n.translateToLocalFormatted("text.gimmestuff:exhaustionLevel", foodStats.foodExhaustionLevel));
		}

	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, world, entityLiving);
		stack.stackSize = 1;
		return stack;
	}

	@Override
	public String getName() {
		return name;
	}
}
