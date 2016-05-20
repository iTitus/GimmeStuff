package io.github.ititus.gimmestuff.client.gui;

import java.util.Map;

import io.github.ititus.gimmestuff.util.stuff.StuffType;
import io.github.ititus.gimmestuff.util.stuff.StuffTypeRegistry;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiInfiniteStuff extends GuiContainer {

	public GuiInfiniteStuff(Container container) {
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		FontRenderer fr = fontRendererObj;
		IForgeRegistry<StuffType> registry = StuffTypeRegistry.getStuffTypeRegistry();

		int offsetX = 0;

		for (Map.Entry<ResourceLocation, StuffType> entry : registry.getEntries()) {
			fr.drawString(entry.getKey().toString(), 0, 0 + offsetX, -1);
			offsetX += fr.FONT_HEIGHT;
		}

	}
}
