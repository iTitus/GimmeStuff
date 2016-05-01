package io.github.ititus.gimmestuff.client.handler;

import io.github.ititus.gimmestuff.GimmeStuff;
import io.github.ititus.gimmestuff.util.JSONUtil;

import net.minecraft.client.Minecraft;

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

}
