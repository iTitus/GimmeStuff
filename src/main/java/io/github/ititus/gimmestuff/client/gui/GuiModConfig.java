package io.github.ititus.gimmestuff.client.gui;

import java.util.stream.Collectors;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.handler.ConfigHandler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiModConfig extends GuiConfig {

	public GuiModConfig(GuiScreen parentScreen) {
		super(parentScreen, ConfigHandler.getConfiguration().getCategoryNames().stream().map(ConfigHandler.getConfiguration()::getCategory).filter(configCategory -> configCategory != null && !configCategory.isChild() && configCategory.showInGui()).map(ConfigElement::new).collect(Collectors.toList()), GimmeStuff.MOD_ID, false, false, I18n.format("text.gimmestuff:config.title"));
	}

}
