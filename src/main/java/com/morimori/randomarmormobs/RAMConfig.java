package com.morimori.randomarmormobs;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class RAMConfig {
	public static ConfigValue<Boolean> isFullSet;
	public static ConfigValue<Boolean> isRandomHeadBlock;
	public static ConfigValue<Boolean> isReplace;
	public static ConfigValue<Integer> Probability;

	public static ConfigValue<Boolean> isHead;
	public static ConfigValue<Boolean> isChest;
	public static ConfigValue<Boolean> isLegs;
	public static ConfigValue<Boolean> isFeet;
	public static ConfigValue<Boolean> isMainHand;
	public static ConfigValue<Boolean> isOffHand;

	public static void init() {
		Pair<ConfigLoder, ForgeConfigSpec> config = new ForgeConfigSpec.Builder().configure(ConfigLoder::new);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, config.getRight());

	}

	static class ConfigLoder {

		public ConfigLoder(ForgeConfigSpec.Builder builder) {
			builder.push("general");
			builder.comment("Whether full set.");
			isFullSet = builder.define("Full set", true);
			builder.comment("Whether block on head.");
			isRandomHeadBlock = builder.define("Block Head", false);
			builder.comment("Whether to replace.");
			isReplace = builder.define("Replace", false);
			builder.comment("Probability of spawning with equipment. (effects of this mod only)\n"
					+ "The smaller the number, the higher the probability.(Invalid below 0)\n"
					+ "0=0%||1=100%||2=50%||3=33%||4=25%...||10=10%");
			Probability = builder.define("Probability", 10);
			builder.comment("Whether to activate each equipment type.");
			isHead = builder.define("Head", true);
			isChest = builder.define("Chest", true);
			isLegs = builder.define("Legs", true);
			isFeet = builder.define("Feet", true);
			isMainHand = builder.define("MainHand", true);
			isOffHand = builder.define("OffHand", true);
			builder.pop();

		}

	}

}
