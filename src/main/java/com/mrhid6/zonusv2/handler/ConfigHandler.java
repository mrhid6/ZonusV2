package com.mrhid6.zonusv2.handler;

import java.io.File;

import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;
	public static boolean testValue = false;

	public static void init(File configFile) {

		// Create the configuration object from the given configuration file.
		if (config == null) {
			config = new Configuration(configFile);
			loadConfig();
		}

	}

	@SubscribeEvent
	public void onConfigChangedEvent(
			ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MOD_ID)) {
			// Resync Config
			loadConfig();
		}
	}

	private static void loadConfig() {

		testValue = config.getBoolean("configValue",
				Configuration.CATEGORY_GENERAL, true, "Example Config");

		if (config.hasChanged()) {
			config.save();
		}
	}

}
