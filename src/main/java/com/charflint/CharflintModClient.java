package com.charflint;
import com.charflint.entity.ModEntities;
import com.charflint.entity.client.ExplodingProjectileRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class CharflintModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.EXPLODING_PROJECTILE,
				ExplodingProjectileRenderer::new);
		EntityRendererRegistry.register(ModEntities.WATER_PROJECTILE, FlyingItemEntityRenderer::new);

	}
}

