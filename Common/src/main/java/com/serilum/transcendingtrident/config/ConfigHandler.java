package com.serilum.transcendingtrident.config;

import com.natamus.collective.config.DuskConfig;
import com.serilum.transcendingtrident.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean mustHoldBucketOfWater = true;
	@Entry(registry = "item") public static String itemsThatAllowRiptide = "minecraft:water_bucket,minecraft:cod_bucket,minecraft:salmon_bucket,minecraft:pufferfish_bucket,minecraft:tropical_fish_bucket,minecraft:axolotl_bucket,minecraft:tadpole_bucket";
	@Entry public static boolean riptideUsesUpTheItem = false;
	@Entry public static boolean canUseRiptideInTheAir = true;
	@Entry(min = 0, max = 20) public static int tridentRiptideUseDuration = 10;
	@Entry(min = 0, max = 100.0) public static double tridentRiptidePowerModifier = 1.0;
	@Entry(min = 0, max = 100.0) public static double tridentRiptideElytraPowerModifier = 1.0;

	public static void initConfig() {
		configMetaData.put("mustHoldBucketOfWater", Arrays.asList(
			"When enabled, Riptide can only be used without rain when the user is holding one of the items of 'itemsThatAllowRiptide' in the other hand, by default a bucket of water."
		));
		configMetaData.put("itemsThatAllowRiptide", Arrays.asList(
			"The items that allow Riptide without rain when held in the other hand. Add minecraft:potion for water bottles, other potions never count."
		));
		configMetaData.put("riptideUsesUpTheItem", Arrays.asList(
			"When enabled, each Riptide launch away from water and rain uses up one of the held items of 'itemsThatAllowRiptide', like firework rockets with an elytra. Buckets turn into an empty bucket and water bottles into a glass bottle."
		));
		configMetaData.put("canUseRiptideInTheAir", Arrays.asList(
			"When disabled, Riptide away from water and rain can only be used while standing on the ground."
		));
		configMetaData.put("tridentRiptideUseDuration", Arrays.asList(
			"The amount of ticks a player needs to charge the trident before being able to use Riptide. Minecraft's default is 10."
		));
		configMetaData.put("tridentRiptidePowerModifier", Arrays.asList(
			"The riptide power of the trident is multiplied by this number on use. 1.0 is the same as vanilla, a higher value allows traveling a greater distance with a single charge."
		));
		configMetaData.put("tridentRiptideElytraPowerModifier", Arrays.asList(
			"Used instead of 'tridentRiptidePowerModifier' while gliding with an elytra. 1.0 is the same as vanilla."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}
