package io.github.ititus.gimmestuff.client.handler;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.client.model.ModelInfiniteFluid;
import io.github.ititus.gimmestuff.util.JSONUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventHandler {

	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event) {
		if (GimmeStuff.DEBUG && (event.isShowAdvancedItemTooltips() || Minecraft.getMinecraft().gameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak)) && event.getItemStack() != null && event.getItemStack().hasTagCompound()) {
			event.getToolTip().add("NBT:");
			try {
				String jsonString = JSONUtil.getJSONString(event.getItemStack().getTagCompound());

				String[] lines = jsonString.split("\n");
				for (String line : lines) {
					event.getToolTip().add(line);
				}
			} catch (Exception e) {
				event.getToolTip().add("ERRROR: " + e.getClass().getSimpleName());
			}

		}
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

}
