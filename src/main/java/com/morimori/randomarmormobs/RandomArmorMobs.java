package com.morimori.randomarmormobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod(modid = "randomarmormobs", name = "RandomArmorMobs", version = "1.1", acceptedMinecraftVersions = "[1.10,1.12.2]")
public class RandomArmorMobs {

	private static List<Item> Head = new ArrayList<Item>();
	private static List<Item> Chest = new ArrayList<Item>();
	private static List<Item> Legs = new ArrayList<Item>();
	private static List<Item> Feet = new ArrayList<Item>();
	private static List<Item> MainHand = new ArrayList<Item>();
	private static List<Item> OffHand = new ArrayList<Item>();
	private static List<Item> Block = new ArrayList<Item>();
	private static HashMap<ItemArmor.ArmorMaterial, Item> Head_map = new HashMap<ItemArmor.ArmorMaterial, Item>();
	private static HashMap<ItemArmor.ArmorMaterial, Item> Chest_map = new HashMap<ItemArmor.ArmorMaterial, Item>();
	private static HashMap<ItemArmor.ArmorMaterial, Item> Legs_map = new HashMap<ItemArmor.ArmorMaterial, Item>();
	private static HashMap<ItemArmor.ArmorMaterial, Item> Feet_map = new HashMap<ItemArmor.ArmorMaterial, Item>();
	private static Set<ItemArmor.ArmorMaterial> ArmorMaterials = new HashSet<ItemArmor.ArmorMaterial>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		RAMConfig.load(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		for (Item i : ForgeRegistries.ITEMS) {
			if (i instanceof ItemArmor) {
				ItemArmor ia = (ItemArmor) i;

				switch (ia.armorType) {
				case CHEST:
					Chest.add(i);
					Chest_map.put((((ItemArmor) i).getArmorMaterial()), i);
					ArmorMaterials.add(((ItemArmor) i).getArmorMaterial());
					break;
				case FEET:
					Feet.add(i);
					Feet_map.put((((ItemArmor) i).getArmorMaterial()), i);
					ArmorMaterials.add(((ItemArmor) i).getArmorMaterial());
					break;
				case HEAD:
					Head.add(i);
					Head_map.put((((ItemArmor) i).getArmorMaterial()), i);
					ArmorMaterials.add(((ItemArmor) i).getArmorMaterial());
					break;
				case LEGS:
					Legs.add(i);
					Legs_map.put((((ItemArmor) i).getArmorMaterial()), i);
					ArmorMaterials.add(((ItemArmor) i).getArmorMaterial());
					break;
				default:
					break;
				}

			}
			if (i instanceof ItemSword || i instanceof ItemAxe || i instanceof ItemPickaxe || i instanceof ItemSpade
					|| i instanceof ItemHoe || i instanceof ItemFishingRod || i == Items.STICK)
				MainHand.add(i);

			if (i instanceof ItemShield || i == Items.TOTEM_OF_UNDYING)
				OffHand.add(i);

			if (i instanceof ItemBlock)
				Block.add(i);
		}

		MinecraftForge.EVENT_BUS.register(this);

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

	@SubscribeEvent
	public void onMobSpawn(LivingSpawnEvent.SpecialSpawn e) {
		Random r = new Random();

		EntityLiving enty = (EntityLiving) e.getEntityLiving();

		if (enty instanceof EntityZombie || enty instanceof AbstractSkeleton || enty instanceof EntityVindicator
				|| enty instanceof EntityVindicator) {
			if (RAMConfig.Probability >= 1) {
				if (r.nextInt(RAMConfig.Probability) == 0) {

					List<Item> MainHanditem = new ArrayList<Item>();
					MainHanditem.addAll(MainHand);

					List<Item> OffHanditem = new ArrayList<Item>();
					OffHanditem.addAll(OffHand);

					if (RAMConfig.isFullSet) {
						List<ItemArmor.ArmorMaterial> aromerlist = new ArrayList<ItemArmor.ArmorMaterial>(
								ArmorMaterials);
						ItemArmor.ArmorMaterial aromer = aromerlist.get(r.nextInt(aromerlist.size()));

						if (RAMConfig.isRandomHeadBlock) {

							setEquipment(enty,
									new ItemStack(MainHanditem.get(r.nextInt(MainHanditem.size()))),
									new ItemStack(OffHanditem.get(r.nextInt(OffHanditem.size()))),
									new ItemStack(Block.get(r.nextInt(Block.size()))),
									new ItemStack(Chest_map.get(aromer)),
									new ItemStack(Legs_map.get(aromer)),
									new ItemStack(Feet_map.get(aromer)));

						} else {

							setEquipment(enty,
									new ItemStack(MainHanditem.get(r.nextInt(MainHanditem.size()))),
									new ItemStack(OffHanditem.get(r.nextInt(OffHanditem.size()))),
									new ItemStack(Head_map.get(aromer)),
									new ItemStack(Chest_map.get(aromer)),
									new ItemStack(Legs_map.get(aromer)),
									new ItemStack(Feet_map.get(aromer)));

						}
					} else {
						if (RAMConfig.isRandomHeadBlock) {

							setEquipment(enty,
									new ItemStack(MainHanditem.get(r.nextInt(MainHanditem.size()))),
									new ItemStack(OffHanditem.get(r.nextInt(OffHanditem.size()))),
									new ItemStack(Block.get(r.nextInt(Block.size()))),
									new ItemStack(Chest.get(r.nextInt(Chest.size()))),
									new ItemStack(Legs.get(r.nextInt(Legs.size()))),
									new ItemStack(Feet.get(r.nextInt(Feet.size()))));

						} else {

							setEquipment(enty,
									new ItemStack(MainHanditem.get(r.nextInt(MainHanditem.size()))),
									new ItemStack(OffHanditem.get(r.nextInt(OffHanditem.size()))),
									new ItemStack(Head.get(r.nextInt(Head.size()))),
									new ItemStack(Chest.get(r.nextInt(Chest.size()))),
									new ItemStack(Legs.get(r.nextInt(Legs.size()))),
									new ItemStack(Feet.get(r.nextInt(Feet.size()))));
						}

					}

				}

			}

		}

	}

	public void setEquipment(EntityLiving entityIn, ItemStack mainhand, ItemStack offhand, ItemStack head,
			ItemStack chest,
			ItemStack legs, ItemStack feet) {
		if (RAMConfig.isMainHand && (entityIn.getHeldItemMainhand().isEmpty()) || RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, mainhand);
		}
		if (RAMConfig.isOffHand && (entityIn.getHeldItemOffhand().isEmpty()) || RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, offhand);
		}

		if (RAMConfig.isHead && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(3).isEmpty()
				|| RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
		}
		if (RAMConfig.isChest
				&& (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(2).isEmpty()
				|| RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
		}
		if (RAMConfig.isLegs && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(1).isEmpty()
				|| RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
		}
		if (RAMConfig.isFeet && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(0).isEmpty()
				|| RAMConfig.isReplace) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.FEET, feet);
		}
	}

}
