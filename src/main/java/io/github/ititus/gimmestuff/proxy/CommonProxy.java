package io.github.ititus.gimmestuff.proxy;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.init.ModItems;
import io.github.ititus.gimmestuff.recipe.RecipeInfiniteItemContentChanger;

import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ModBlocks.preInit();
		ModItems.preInit();
		FMLInterModComms.sendMessage("Waila", "register", "io.github.ititus.gimmestuff.compat.waila.CompatWaila.callbackRegister");
	}

	public void init(FMLInitializationEvent event) {

		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockInfiniteItem, 1, 0), new ItemStack(ModBlocks.blockInfiniteItem, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.blockInfiniteItem, 1, 1), new ItemStack(ModBlocks.blockInfiniteItem, 1, 1));

		GameRegistry.addRecipe(new RecipeInfiniteItemContentChanger());
		RecipeSorter.register(GimmeStuff.MOD_ID + ":infiniteItemContentChanger", RecipeInfiniteItemContentChanger.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}

	public void postInit(FMLPostInitializationEvent event) {
	}
}
