package io.github.ititus.gimmestuff.handler;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (GimmeStuff.MOD_ID.equalsIgnoreCase(event.getModID())) {
			ConfigHandler.loadConfig();
		}
	}

}
