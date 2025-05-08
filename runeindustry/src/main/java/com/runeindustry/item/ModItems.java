package com.runeindustry.item;
import com.runeindustry.RuneIndustryMod;

import com.runeindustry.item.custom.StaffItem;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

	public static final Item FIRE_RUNE = registerItem("fire_rune", new Item(new FabricItemSettings()));
	public static final Item FIREBALL_STAFF = registerItem("fireball_staff", new Item(new FabricItemSettings().maxCount(1)));

	public static final Item FIREBALL_STAFF_IRON = registerItem("attune_staff_iron",
			new StaffItem(new FabricItemSettings().maxDamage(250)));
	public static final Item FIREBALL_STAFF_COPPER = registerItem("attune_staff_copper",
			new StaffItem(new FabricItemSettings().maxDamage(250)));
	public static final Item FIREBALL_STAFF_GOLD = registerItem("attune_staff_gold",
			new StaffItem(new FabricItemSettings().maxDamage(250)));
	public static final Item FIREBALL_STAFF_DIAMOND = registerItem("attune_staff_diamond",
			new StaffItem(new FabricItemSettings().maxDamage(250)));
	public static final Item BLANK_RUNE = registerItem("blank_rune", new Item(new FabricItemSettings()));
	public static final Item EXPLO_RUNE = registerItem("explo_rune", new Item(new FabricItemSettings()));
	public static final Item WATER_RUNE = registerItem("water_rune", new Item(new FabricItemSettings()));
	public static final Item EARTH_RUNE = registerItem("earth_rune", new Item(new FabricItemSettings()));
	public static final Item AIR_RUNE = registerItem("air_rune", new Item(new FabricItemSettings()));



	private static Item registerItem(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(item));
		return Registry.register(Registries.ITEM, new Identifier(RuneIndustryMod.MOD_ID, name), item);
	}

	public static void registerModItems() {
		RuneIndustryMod.LOGGER.info("Registering ModItems for " + RuneIndustryMod.MOD_ID);
	}
}
