package io.github.ititus.gimmestuff.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;

public class RenderUtil {

	public static int getColorInt(EnumDyeColor color) {
		float[] colorFloats = EntitySheep.getDyeRgb(color);
		int r = ((int) (colorFloats[0] * 255)) & 0xFF;
		int g = ((int) (colorFloats[1] * 255)) & 0xFF;
		int b = ((int) (colorFloats[2] * 255)) & 0xFF;
		int colorInt = 0;
		colorInt |= r;
		colorInt |= (g << 8);
		colorInt |= (b << 16);
		return colorInt;
	}

	public static void colorRGBA(int color) {
		float a = getAlpha(color) / 255F;
		float r = getRed(color) / 255F;
		float g = getGreen(color) / 255F;
		float b = getBlue(color) / 255F;
		GlStateManager.color(r, g, b, a);
	}

	public static int getAlpha(int color) {
		return (color >> 24) & 0xFF;
	}

	public static int getRed(int color) {
		return (color >> 16) & 0xFF;
	}

	public static int getGreen(int color) {
		return (color >> 8) & 0xFF;
	}

	public static int getBlue(int color) {
		return color & 0xFF;
	}

	public static void drawFluidInTank(FluidStack fluidStack, int capacity, int x, int y, int width, int height) {
		if (fluidStack == null || fluidStack.getFluid() == null | fluidStack.amount <= 0) {
			return;
		}
		TextureAtlasSprite icon = getAtlasSprite(fluidStack.getFluid().getStill(fluidStack));
		bindTexture(TextureMap.locationBlocksTexture);
		colorRGBA(fluidStack.getFluid().getColor(fluidStack));
		int level = (int) ((fluidStack.amount / (double) capacity) * height);
		int fullX = width / 16;
		int fullY = height / 16;
		int lastX = width - fullX * 16;
		int lastY = height - fullY * 16;
		int fullLvl = (height - level) / 16;
		int lastLvl = (height - level) - fullLvl * 16;
		for (int i = 0; i < fullX; i++) {
			for (int j = 0; j < fullY; j++) {
				if (j >= fullLvl) {
					drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
				}
			}
		}
		for (int i = 0; i < fullX; i++) {
			drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
		}
		for (int i = 0; i < fullY; i++) {
			if (i >= fullLvl) {
				drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
			}
		}
		drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
	}

	public static void drawCutIcon(TextureAtlasSprite icon, double x, double y, double width, double height, double cut) {
		double zLevel = 0;

		Tessellator t = Tessellator.getInstance();
		VertexBuffer vb = t.getBuffer();
		vb.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		vb.pos(x, y + height, zLevel).tex(icon.getMinU(), icon.getInterpolatedV(height)).endVertex();
		vb.pos(x + width, y + height, zLevel).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(height)).endVertex();
		vb.pos(x + width, y + cut, zLevel).tex(icon.getInterpolatedU(width), icon.getInterpolatedV(cut)).endVertex();
		vb.pos(x, y + cut, zLevel).tex(icon.getMinU(), icon.getInterpolatedV(cut)).endVertex();
		t.draw();
	}

	public static TextureAtlasSprite getAtlasSprite(ResourceLocation texture) {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(texture.toString());
	}

	public static void bindTexture(ResourceLocation texture) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
	}

	public static void putTexturedQuad(VertexBuffer vb, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int color, int brightness) {
		int l1 = brightness >> 0x10 & 0xFFFF;
		int l2 = brightness & 0xFFFF;

		int a = color >> 24 & 0xFF;
		int r = color >> 16 & 0xFF;
		int g = color >> 8 & 0xFF;
		int b = color & 0xFF;

		putTexturedQuad(vb, sprite, x, y, z, w, h, d, face, r, g, b, a, l1, l2);
	}

	public static void putTexturedQuad(VertexBuffer vb, TextureAtlasSprite sprite, double x, double y, double z, double w, double h, double d, EnumFacing face, int r, int g, int b, int a, int light1, int light2) {
		double minU = sprite.getMinU();
		double maxU = sprite.getMaxU();
		double minV = sprite.getMinV();
		double maxV = sprite.getMaxV();

		double x1 = x;
		double x2 = x + w;
		double y1 = y;
		double y2 = y + h;
		double z1 = z;
		double z2 = z + d;

		switch (face) {
			case DOWN:
			case UP:
				minU = sprite.getInterpolatedU(x1 * 16d);
				maxU = sprite.getInterpolatedU(x2 * 16d);
				minV = sprite.getInterpolatedV(z1 * 16d);
				maxV = sprite.getInterpolatedV(z2 * 16d);
				break;
			case NORTH:
			case SOUTH:
				minU = sprite.getInterpolatedU(x1 * 16f);
				maxU = sprite.getInterpolatedU(x2 * 16f);
				minV = sprite.getInterpolatedV(y1 * 16f);
				maxV = sprite.getInterpolatedV(y2 * 16f);
				break;
			case WEST:
			case EAST:
				minU = sprite.getInterpolatedU(z1 * 16d);
				maxU = sprite.getInterpolatedU(z2 * 16d);
				minV = sprite.getInterpolatedV(y1 * 16d);
				maxV = sprite.getInterpolatedV(y2 * 16d);
				break;
		}

		switch (face) {
			case DOWN:
				vb.pos(x1, y1, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				break;
			case UP:
				vb.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y2, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case NORTH:
				vb.pos(x1, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				break;
			case SOUTH:
				vb.pos(x1, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case WEST:
				vb.pos(x1, y1, z1).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y1, z2).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y2, z2).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x1, y2, z1).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				break;
			case EAST:
				vb.pos(x2, y1, z1).color(r, g, b, a).tex(minU, maxV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z1).color(r, g, b, a).tex(minU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y2, z2).color(r, g, b, a).tex(maxU, minV).lightmap(light1, light2).endVertex();
				vb.pos(x2, y1, z2).color(r, g, b, a).tex(maxU, maxV).lightmap(light1, light2).endVertex();
				break;
		}
	}

}
