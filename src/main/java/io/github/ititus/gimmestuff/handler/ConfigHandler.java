package io.github.ititus.gimmestuff.handler;

import java.io.File;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	private static Configuration cfg_main;

	public static void preInit(FMLPreInitializationEvent event) {
		File configFolder = new File(event.getModConfigurationDirectory(), GimmeStuff.MOD_ID);
		cfg_main = new Configuration(new File(configFolder, "main.cfg"));
		loadConfig();
	}

	public static void loadConfig() {


		if (cfg_main.hasChanged()) {
			cfg_main.save();
		}
	}

	public static Configuration getConfiguration() {
		return cfg_main;
	}

}
