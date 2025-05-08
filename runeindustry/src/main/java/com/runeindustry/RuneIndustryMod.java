package com.runeindustry;

import com.runeindustry.entity.ModEntities;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import com.runeindustry.item.ModItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuneIndustryMod implements ModInitializer {
	public static final String MOD_ID = "runeindustry";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModEntities.registerModEntities();
	}
}
