package io.github.ititus.gimmestuff.proxy;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.block.BlockInfiniteFluid;
import io.github.ititus.gimmestuff.client.handler.ClientEventHandler;
import io.github.ititus.gimmestuff.client.model.ModelInfiniteFluid;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.init.ModItems;
import io.github.ititus.gimmestuff.item.ItemBlockInfiniteFluid;
import io.github.ititus.gimmestuff.util.INameable;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

		registerItemModel(ModBlocks.blockInfiniteItem);
		registerItemModel(ModBlocks.blockInfiniteItem, 1, getModelResLoc(ModBlocks.blockInfiniteItem.getName(), "inventory"));

		registerItemModel(ModBlocks.blockInfiniteFluid);
		registerItemModel(ModBlocks.blockInfiniteRF);

		registerItemModel(ModItems.itemGodFood);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(
				(state, world, pos, tintIndex) -> {
					if (state instanceof IExtendedBlockState) {
						FluidStack fluidStack = ((IExtendedBlockState) state).getValue(BlockInfiniteFluid.FLUID);
						if (fluidStack != null) {
							return fluidStack.getFluid().getColor(fluidStack);
						}
					}
					return -1;
				},
				ModBlocks.blockInfiniteFluid
		);
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(
				(stack, tintIndex) -> {
					FluidStack fluidStack = ItemBlockInfiniteFluid.getFluidStack(stack);
					if (fluidStack != null) {
						return fluidStack.getFluid().getColor(fluidStack);
					}
					return -1;
				},
				ModBlocks.blockInfiniteFluid
		);
	}

	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {

		ResourceLocation resourceLocation = new ResourceLocation(GimmeStuff.MOD_ID, "block/blockInfiniteFluid");
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(new ResourceLocation(GimmeStuff.MOD_ID, "blockInfiniteFluid"), "inventory");

		try {
			IModel model = ModelLoaderRegistry.getModel(resourceLocation);
			if (model instanceof IRetexturableModel) {
				IRetexturableModel infiniteFluidModel = (IRetexturableModel) model;
				IBakedModel inventoryModel = event.getModelRegistry().getObject(modelResourceLocation);
				if (inventoryModel instanceof IPerspectiveAwareModel) {
					IBakedModel finalModel = new ModelInfiniteFluid((IPerspectiveAwareModel) inventoryModel, infiniteFluidModel, DefaultVertexFormats.BLOCK);
					event.getModelRegistry().putObject(modelResourceLocation, finalModel);
				}

				modelResourceLocation = new ModelResourceLocation(new ResourceLocation(GimmeStuff.MOD_ID, "blockInfiniteFluid"), "normal");
				IBakedModel normalModel = event.getModelRegistry().getObject(modelResourceLocation);
				if (normalModel instanceof IPerspectiveAwareModel) {
					IBakedModel finalModel = new ModelInfiniteFluid((IPerspectiveAwareModel) normalModel, infiniteFluidModel, DefaultVertexFormats.BLOCK);
					event.getModelRegistry().putObject(modelResourceLocation, finalModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private ModelResourceLocation getModelResLoc(String path, String variant) {
		return new ModelResourceLocation(GimmeStuff.MOD_ID + ":" + path, variant);
	}

	private <T extends Block & INameable> void registerItemModel(T block) {
		registerItemModel(block, 0, getModelResLoc(block.getName(), "inventory"));
	}

	private <T extends Item & INameable> void registerItemModel(T item) {
		registerItemModel(item, 0, getModelResLoc(item.getName(), "inventory"));
	}

	private void registerItemModel(Block block, int meta, ModelResourceLocation modelResourceLocation) {
		registerItemModel(Item.getItemFromBlock(block), meta, modelResourceLocation);
	}

	private void registerItemModel(Item item, int meta, ModelResourceLocation modelResourceLocation) {
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

}
