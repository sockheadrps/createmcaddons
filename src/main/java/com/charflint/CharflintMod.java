package com.charflint;

import com.charflint.block.ModBlocks;
import com.charflint.entity.ModEntities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import com.charflint.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharflintMod implements ModInitializer {
	public static final String MOD_ID = "charflint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModEntities.registerModEntities();
	}
}
