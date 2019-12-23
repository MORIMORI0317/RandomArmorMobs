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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
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
import net.minecraft.item.ItemTrident;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("randomarmormobs")
public class RandomArmorMobs {
	List<Item> Head = new ArrayList<Item>();
	List<Item> Chest = new ArrayList<Item>();
	List<Item> Legs = new ArrayList<Item>();
	List<Item> Feet = new ArrayList<Item>();
	List<Item> MainHand = new ArrayList<Item>();
	List<Item> OffHand = new ArrayList<Item>();
	List<Item> Block = new ArrayList<Item>();
	HashMap<IArmorMaterial, Item> Head_map = new HashMap<IArmorMaterial, Item>();
	HashMap<IArmorMaterial, Item> Chest_map = new HashMap<IArmorMaterial, Item>();
	HashMap<IArmorMaterial, Item> Legs_map = new HashMap<IArmorMaterial, Item>();
	HashMap<IArmorMaterial, Item> Feet_map = new HashMap<IArmorMaterial, Item>();
	Set<IArmorMaterial> ArmorMaterials = new HashSet<IArmorMaterial>();

	public static final Tag<Item> MAINHANDITEM = new ItemTags.Wrapper(
			new ResourceLocation("randomarmormobs", "mainhanditem"));

	public static final Tag<Item> OFFHANDITEM = new ItemTags.Wrapper(
			new ResourceLocation("randomarmormobs", "offhanditem"));

	public RandomArmorMobs() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		MinecraftForge.EVENT_BUS.register(this);
		RAMConfig.init();
	}

	private void processIMC(final InterModProcessEvent event) {
		for (Item i : ForgeRegistries.ITEMS) {
			if (i instanceof ItemArmor)
				switch (((ItemArmor) i).getEquipmentSlot()) {
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

			if (i instanceof ItemSword || i instanceof ItemAxe || i instanceof ItemPickaxe || i instanceof ItemSpade
					|| i instanceof ItemHoe || i instanceof ItemFishingRod || i instanceof ItemTrident)
				MainHand.add(i);

			if (i instanceof ItemShield)
				OffHand.add(i);

			if (i instanceof ItemBlock)
				Block.add(i);

		}

	}

	@SubscribeEvent
	public void onMobSpawn(LivingSpawnEvent.SpecialSpawn e) {
		Random r = new Random();

		EntityLiving enty = (EntityLiving) e.getEntityLiving();

		if (enty instanceof EntityZombie || enty instanceof AbstractSkeleton || enty instanceof EntityVindicator
				|| enty instanceof EntityVindicator) {
			if (RAMConfig.Probability.get() >= 1) {
				if (r.nextInt(RAMConfig.Probability.get()) == 0) {

					List<Item> MainHanditem = new ArrayList<Item>();
					MainHanditem.addAll(MainHand);
					MainHanditem.addAll(MAINHANDITEM.getAllElements());
					List<Item> OffHanditem = new ArrayList<Item>();
					OffHanditem.addAll(OffHand);
					OffHanditem.addAll(OFFHANDITEM.getAllElements());

					if (RAMConfig.isFullSet.get()) {
						List<IArmorMaterial> aromerlist = new ArrayList<IArmorMaterial>(ArmorMaterials);
						IArmorMaterial aromer = aromerlist.get(r.nextInt(aromerlist.size()));

						if (RAMConfig.isRandomHeadBlock.get()) {

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
						if (RAMConfig.isRandomHeadBlock.get()) {

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
		if (RAMConfig.isMainHand.get() && (entityIn.getHeldItemMainhand().isEmpty()) || RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, mainhand);
		}
		if (RAMConfig.isOffHand.get() && (entityIn.getHeldItemOffhand().isEmpty()) || RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, offhand);
		}

		if (RAMConfig.isHead.get() && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(3).isEmpty()
				|| RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.HEAD, head);
		}
		if (RAMConfig.isChest.get()
				&& (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(2).isEmpty()
				|| RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.CHEST, chest);
		}
		if (RAMConfig.isLegs.get() && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(1).isEmpty()
				|| RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.LEGS, legs);
		}
		if (RAMConfig.isFeet.get() && (Lists.newArrayList(entityIn.getArmorInventoryList().iterator())).get(0).isEmpty()
				|| RAMConfig.isReplace.get()) {
			entityIn.setItemStackToSlot(EntityEquipmentSlot.FEET, feet);
		}
	}
}
