package io.github.ititus.gimmestuff.client.gui;

import io.github.ititus.gimmestuff.util.stuff.ModuleRegistry;
import io.github.ititus.gimmestuff.util.stuff.module.Module;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
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
		FMLControlledNamespacedRegistry<Module> registry = ModuleRegistry.getModuleRegistry();

		int offsetX = 0;

		for (ResourceLocation resourceLocation : registry.getKeys()) {
			fr.drawString(resourceLocation.toString(), 0, offsetX, -1);
			offsetX += fr.FONT_HEIGHT;
		}

	}
}
