package io.github.ititus.gimmestuff.client.handler;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.client.model.ModelInfiniteFluid;
import io.github.ititus.gimmestuff.init.ModBlocks;
import io.github.ititus.gimmestuff.tile.TileInfiniteItem;
import io.github.ititus.gimmestuff.util.JSONUtil;
import io.github.ititus.gimmestuff.util.Utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
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

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
			return;
		}

		Minecraft mc = Minecraft.getMinecraft();
		RayTraceResult result = mc.objectMouseOver;

		if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
			BlockPos pos = result.getBlockPos();
			IBlockState state = mc.theWorld.getBlockState(pos);

			if (state.getBlock() == ModBlocks.blockInfiniteItem) {
				TileEntity tile = mc.theWorld.getTileEntity(pos);
				if (tile instanceof TileInfiniteItem) {
					renderInfiniteItemHUD(event, (TileInfiniteItem) tile);
				}
			}
		}

	}

	private void renderInfiniteItemHUD(RenderGameOverlayEvent.Post event, TileInfiniteItem tile) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = event.getResolution();
		ItemStack[] stacks = tile.getStacks();

		int count = Utils.countNonNull(stacks);
		int offsetY = 0;

		if (count > 0) {
			String itemsText = TextFormatting.GRAY + I18n.format("text.gimmestuff:items");
			mc.fontRendererObj.drawString(itemsText, 2, res.getScaledHeight() / 4 + offsetY, -1);
			offsetY += mc.fontRendererObj.FONT_HEIGHT;
			for (int i = 0; i < stacks.length; i++) {
				ItemStack itemStack = stacks[i];
				if (itemStack != null) {
					EnumRarity rarity = itemStack.getRarity();
					String itemNameString = TextFormatting.GRAY + "-     " + (rarity != null ? rarity.rarityColor : EnumRarity.COMMON.rarityColor) + (itemStack.hasDisplayName() ? TextFormatting.ITALIC : "") + itemStack.getDisplayName();

					mc.fontRendererObj.drawString(itemNameString, 2, res.getScaledHeight() / 4 + offsetY + 5, -1);

					int w = mc.fontRendererObj.getStringWidth("-");

					RenderHelper.enableGUIStandardItemLighting();
					mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, 2 + w + 1, res.getScaledHeight() / 4 + offsetY);
					RenderHelper.disableStandardItemLighting();

					offsetY += 16;
				}
			}
		}
	}

}
