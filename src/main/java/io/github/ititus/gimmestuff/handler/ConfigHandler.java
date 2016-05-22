package io.github.ititus.gimmestuff.handler;

import java.io.File;

import io.github.ititus.gimmestuff.GimmeStuff;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {

	public static final String CATEGORY_DEBUG = Configuration.CATEGORY_GENERAL + ".debug";

	public static boolean showNBT = false;
	private static Configuration cfg;

	public static void preInit(FMLPreInitializationEvent event) {
		File configFolder = new File(event.getModConfigurationDirectory(), GimmeStuff.MOD_ID);
		cfg = new Configuration(new File(configFolder, "main.cfg"));
		loadConfig();
	}

	public static void loadConfig() {

		Property p;

		p = cfg.get(CATEGORY_DEBUG, "showNBT", showNBT);
		showNBT = p.getBoolean();

		if (cfg.hasChanged()) {
			cfg.save();
		}
	}

	public static Configuration getConfiguration() {
		return cfg;
	}

}
