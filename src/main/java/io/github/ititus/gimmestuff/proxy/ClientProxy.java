package io.github.ititus.gimmestuff.proxy;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.init.ModBlocks;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ModBlocks.blockInfiniteFluid), 0, getModelResLoc(ModBlocks.blockInfiniteFluid.getName(), "inventory"));

	}

	private ModelResourceLocation getModelResLoc(String path, String variant) {
		return new ModelResourceLocation(GimmeStuff.MOD_ID + ":" + path, variant);
	}

}
