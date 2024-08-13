package com.fabien_gigante;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ModInitializer;

public class EssentialSpongeMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("essential-sponge");
	
	// Server-side mod entry point
	@Override
	public void onInitialize() {
		LOGGER.info("Essential Sponge - Mod starting...");
	}
}