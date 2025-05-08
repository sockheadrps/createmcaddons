package com.runeindustry.entity;

import com.runeindustry.RuneIndustryMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

	public static final EntityType<CustomFireballEntity> CUSTOM_FIREBALL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(RuneIndustryMod.MOD_ID, "custom_fireball"),
			FabricEntityTypeBuilder.<CustomFireballEntity>create(SpawnGroup.MISC, CustomFireballEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.trackRangeChunks(4)
					.trackedUpdateRate(10)
					.build()
	);

	public static final EntityType<ExplodingProjectileEntity> EXPLODING_PROJECTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(RuneIndustryMod.MOD_ID, "exploding_projectile"),
			FabricEntityTypeBuilder.<ExplodingProjectileEntity>create(SpawnGroup.MISC, ExplodingProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.trackRangeChunks(4)
					.trackedUpdateRate(10)
					.build()
	);
	public static final EntityType<WaterProjectileEntity> WATER_PROJECTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(RuneIndustryMod.MOD_ID, "water_projectile"),
			FabricEntityTypeBuilder.<WaterProjectileEntity>create(SpawnGroup.MISC, WaterProjectileEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.trackRangeChunks(4)
					.trackedUpdateRate(10)
					.build()
	);

	public static void registerModEntities() {
		RuneIndustryMod.LOGGER.info("Registering custom entities for " + RuneIndustryMod.MOD_ID);
	}
}
