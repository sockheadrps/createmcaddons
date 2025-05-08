package com.runeindustry;
import com.runeindustry.entity.ModEntities;
import com.runeindustry.entity.client.ExplodingProjectileRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class RuneIndustryModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.EXPLODING_PROJECTILE,
				ExplodingProjectileRenderer::new);
		EntityRendererRegistry.register(ModEntities.WATER_PROJECTILE, FlyingItemEntityRenderer::new);

	}
}

