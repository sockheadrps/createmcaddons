package com.charflint.item;

import com.charflint.CharflintMod;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

	public static final Item MOLTEN_CHARFLINT = registerItem("molten_charflint", new Item(new FabricItemSettings()));



	private static Item registerItem(String name, Item item) {
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(item));
		return Registry.register(Registries.ITEM, new Identifier(CharflintMod.MOD_ID, name), item);
	}

	public static void registerModItems() {
		CharflintMod.LOGGER.info("Registering ModItems for " + CharflintMod.MOD_ID);
	}
}
