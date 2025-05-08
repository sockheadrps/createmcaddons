package com.runeindustry.entity;

import com.runeindustry.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.*;


public class WaterProjectileEntity extends ThrownItemEntity {

	private static final Map<LivingEntity, Integer> frozenEntities = new HashMap<>();
	private int freezeDuration = 20;



	public WaterProjectileEntity(EntityType<? extends ThrownItemEntity> type, World world) {
		super(type, world);
		this.freezeDuration = 20;
	}

	public WaterProjectileEntity(World world, LivingEntity owner, double x, double y, double z, int freezeDuration) {
		super(ModEntities.WATER_PROJECTILE, owner, world);
		this.setPosition(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
		this.setVelocity(x, y, z, 1.5F, 1.0F);
		this.freezeDuration = freezeDuration;
	}

	@Override
	protected ItemStack getItem() {
		return new ItemStack(ModItems.WATER_RUNE);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.WATER_RUNE;
	}

	public static void unfreeze(LivingEntity entity) {
		if (frozenEntities.containsKey(entity)) {
			entity.setNoGravity(false);
			entity.setFrozenTicks(0);
			frozenEntities.remove(entity);
		}
	}


	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);

		if (!this.getWorld().isClient) {
			Box area = new Box(this.getBlockPos()).expand(1);
			List<LivingEntity> targets = this.getWorld().getEntitiesByClass(
					LivingEntity.class, area, e -> e != this.getOwner() && e.isAlive()
			);

			for (LivingEntity target : targets) {
				target.setFrozenTicks(60);
				target.setVelocity(0, 0.05, 0);
				target.velocityModified = true;
				target.setNoGravity(true);
				frozenEntities.put(target, this.freezeDuration);
			}

			for (LivingEntity target : targets) {
				((ServerWorld) this.getWorld()).spawnParticles(
						ParticleTypes.BUBBLE, target.getX(), target.getY(), target.getZ(),
						15, 0.5, 0.5, 0.5, 0.05
				);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (!this.getWorld().isClient) {
			Iterator<Map.Entry<LivingEntity, Integer>> iterator = frozenEntities.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<LivingEntity, Integer> entry = iterator.next();
				LivingEntity target = entry.getKey();
				int ticksLeft = entry.getValue() - 1;

				if (ticksLeft <= 0) {
					target.setNoGravity(false);

					((ServerWorld) this.getWorld()).spawnParticles(
							ParticleTypes.CLOUD,
							target.getX(), target.getY(), target.getZ(),
							10, 0.3, 0.3, 0.3, 0.02
					);

					iterator.remove();
				} else {
					entry.setValue(ticksLeft);

					// Floating effect while levitating
					target.setVelocity(0, 0.03, 0);
					target.velocityModified = true;

					((ServerWorld) this.getWorld()).spawnParticles(
							ParticleTypes.SPLASH,
							target.getX(), target.getY() + 0.5, target.getZ(),
							2, 0.2, 0.2, 0.2, 0.01
					);
				}
			}
		} else {
			this.getWorld().addParticle(ParticleTypes.SPLASH, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
		}
	}
}
