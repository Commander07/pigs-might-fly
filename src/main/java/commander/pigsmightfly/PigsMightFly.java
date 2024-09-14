package commander.pigsmightfly;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PigsMightFly implements ModInitializer {
	public static final String MOD_ID = "pigs-might-fly";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Items.initialize();
		Entities.initialize();
	}
}