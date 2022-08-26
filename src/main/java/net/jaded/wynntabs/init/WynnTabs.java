package net.jaded.wynntabs.init;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WynnTabs implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("wynntabs");

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
	}
}
