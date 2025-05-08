package com.runeindustry.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CustomFireballEntity extends SmallFireballEntity {

	private int customDamage = 2;

	// Constructor used by Fabric for entity registration
	public CustomFireballEntity(EntityType<? extends SmallFireballEntity> type, World world) {
		super(type, world);
	}

	// Constructor used when player casts the spell
	public CustomFireballEntity(World world, LivingEntity owner, double x, double y, double z) {
		super(world, owner, x, y, z);
	}

	// Constructor for spawning at specific coordinates and velocity
	public CustomFireballEntity(World world, double x, double y, double z, double dx, double dy, double dz) {
		super(world, x, y, z, dx, dy, dz);
	}

	// Set custom damage
	public void setCustomDamage(int damage) {
		this.customDamage = damage;
	}

	// Override hit entity to apply custom damage
	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity target = entityHitResult.getEntity();
		if (!this.getWorld().isClient) {
			DamageSource source = this.getDamageSources().fireball(this, this.getOwner());
			target.damage(source, customDamage);
			this.discard(); // remove the fireball after hit
		}
	}

	// Optional: make it disappear on any collision
	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.getWorld().isClient) {
			this.discard();
		}
	}

	// Optional: override dimensions (helps with hitbox issues)
	@Override
	public EntityDimensions getDimensions(EntityPose pose) {
		return EntityDimensions.fixed(0.3125F, 0.3125F);
	}
}
