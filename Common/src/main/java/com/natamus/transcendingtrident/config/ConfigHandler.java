package com.natamus.transcendingtrident.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.transcendingtrident.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean mustHoldBucketOfWater = true;
	@Entry(min = 0, max = 20) public static int tridentUseDuration = 5;
	@Entry(min = 0, max = 100.0) public static double tridentUsePowerModifier = 3.0;

	public static void initConfig() {
		configMetaData.put("mustHoldBucketOfWater", Arrays.asList(
			"When enabled, Riptide can only be used without rain when the user is holding a bucket of water."
		));
		configMetaData.put("tridentUseDuration", Arrays.asList(
			"The amount of time a player needs to charge the trident before being able to use Riptide. Minecraft's default is 10."
		));
		configMetaData.put("tridentUsePowerModifier", Arrays.asList(
			"The riptide power of the trident is multiplied by this number on use. Allows traveling a greater distance with a single charge."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}