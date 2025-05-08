package com.charflint.entity.client;

import com.charflint.entity.ExplodingProjectileEntity;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class ExplodingProjectileRenderer extends EntityRenderer<ExplodingProjectileEntity> {

	public ExplodingProjectileRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public Identifier getTexture(ExplodingProjectileEntity entity) {
		return new Identifier("charflint", "textures/entity/exploding_projectile.png");
	}
}
