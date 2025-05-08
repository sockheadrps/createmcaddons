package com.runeindustry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ExplodingProjectileEntity extends SmallFireballEntity {
	private int lifeTicks = 0;


	public ExplodingProjectileEntity(EntityType<? extends SmallFireballEntity> type, World world) {
		super(type, world);
	}

	public ExplodingProjectileEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
		super(ModEntities.EXPLODING_PROJECTILE, world);
		this.setOwner(owner);
		this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
		this.setVelocity(directionX, directionY, directionZ, 1.5F, 1.0F); // speed and inaccuracy
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		if (!this.getWorld().isClient) {
			this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.9f, World.ExplosionSourceType.MOB);
			this.discard(); // remove projectile
		}
		super.onCollision(hitResult);
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.getWorld().isClient) {
			lifeTicks++;
			if (lifeTicks > 80) {
				this.discard();
			}
		}
	}
}
