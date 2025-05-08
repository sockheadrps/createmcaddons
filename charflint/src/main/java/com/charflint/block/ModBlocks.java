package com.charflint.block;

import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import com.charflint.CharflintMod;

public class ModBlocks {

	public static final Block FLINT_CHARCOAL_BLOCK = registerBlock("flint_charcoal_block",
			new Block(Block.Settings.create()
					.mapColor(MapColor.STONE_GRAY)
					.strength(4.0f)
			)
	);

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block); // <-- 🛠 Add this line!
		return Registry.register(Registries.BLOCK, new Identifier(CharflintMod.MOD_ID, name), block);
	}

	private static void registerBlockItem(String name, Block block) {
		BlockItem blockItem = new BlockItem(block, new FabricItemSettings());
		Registry.register(Registries.ITEM, new Identifier(CharflintMod.MOD_ID, name), blockItem);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> entries.add(blockItem));
	}

	public static void registerModBlocks() {
		CharflintMod.LOGGER.info("Registering ModBlocks for " + CharflintMod.MOD_ID);
	}
}
