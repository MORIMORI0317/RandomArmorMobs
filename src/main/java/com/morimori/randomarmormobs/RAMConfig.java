package com.morimori.randomarmormobs;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class RAMConfig {

	public static Configuration config;

	public static boolean isFullSet;
	public static boolean isRandomHeadBlock;
	public static boolean isReplace;
	public static int Probability;
	public static boolean isHead;
	public static boolean isChest;
	public static boolean isLegs;
	public static boolean isFeet;
	public static boolean isMainHand;
	public static boolean isOffHand;

	public static void load(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		load();
		sync();

		MinecraftForge.EVENT_BUS.register(new RAMConfig());

	}

	public static void sync() {

		//		config.getCategory("general").setComment("RandomArmorMobs Config");

		isFullSet = config
				.get("general", "Full set", true, "Whether full set.")
				.getBoolean(true);
		isRandomHeadBlock = config
				.get("general", "Block Head", false, "Whether block on head.")
				.getBoolean(false);
		isReplace = config
				.get("general", "Replace", false, "Whether to replace.")
				.getBoolean(false);
		Probability = config
				.get("general", "Probability", 10,
						"Probability of spawning with equipment. (effects of this mod only)\n"
								+ "The smaller the number, the higher the probability.(Invalid below 0)\n"
								+ "0=0%||1=100%||2=50%||3=33%||4=25%...||10=10%")
				.getInt(10);

		isHead = config
				.get("general", "Head", true, "Whether to activate each equipment type.")
				.getBoolean(true);
		isChest = config
				.get("general", "Chest", true, "Whether to activate each equipment type.")
				.getBoolean(true);
		isLegs = config
				.get("general", "Legs", true, "Whether to activate each equipment type.")
				.getBoolean(true);
		isFeet = config
				.get("general", "Feet", true, "Whether to activate each equipment type.")
				.getBoolean(true);
		isMainHand = config
				.get("general", "MainHand", true, "Whether to activate each equipment type.")
				.getBoolean(true);
		isOffHand = config
				.get("general", "OffHand", true, "Whether to activate each equipment type.")
				.getBoolean(true);


		if (config.hasChanged())
			save();
	}

	public static void save() {
		config.save();
	}

	public static void load() {
		config.load();
	}

}
