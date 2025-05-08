package com.charflint.item.custom;

import com.charflint.entity.CustomFireballEntity;
import com.charflint.entity.ExplodingProjectileEntity;
import com.charflint.entity.WaterProjectileEntity;
import com.charflint.item.ModItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class StaffItem extends Item {

	public StaffItem(Settings settings) {
		super(settings.maxDamage(250));
	}

	@Override
	public Text getName(ItemStack stack) {
		if (stack.isOf(ModItems.FIREBALL_STAFF_IRON)) return Text.literal("§7Iron-Tuned Fire Staff");
		if (stack.isOf(ModItems.FIREBALL_STAFF_COPPER)) return Text.literal("§6Copper-Tuned Fire Staff");
		if (stack.isOf(ModItems.FIREBALL_STAFF_GOLD)) return Text.literal("§eGolden Fire Staff");
		if (stack.isOf(ModItems.FIREBALL_STAFF_DIAMOND)) return Text.literal("§bDiamond Fire Staff");
		return super.getName(stack);
	}

	private int getDamageFromStaff(Item staffItem) {
		if (staffItem == ModItems.FIREBALL_STAFF_IRON) return 4;
		if (staffItem == ModItems.FIREBALL_STAFF_COPPER) return 6;
		if (staffItem == ModItems.FIREBALL_STAFF_GOLD) return 8;
		if (staffItem == ModItems.FIREBALL_STAFF_DIAMOND) return 12;
		return 2; // default
	}

	private boolean isValidRune(Item item) {
		return item == ModItems.FIRE_RUNE || item == ModItems.EXPLO_RUNE || item == ModItems.WATER_RUNE ||
				item == ModItems.EARTH_RUNE || item == ModItems.AIR_RUNE;
	}


	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack staffStack = user.getStackInHand(hand);
		ItemStack offhandStack = user.getOffHandStack();
		Item offhandItem = offhandStack.getItem();



		// Make sure there's a rune in the offhand
		if (offhandStack.isEmpty() || !isValidRune(offhandItem)) {
			user.sendMessage(Text.of("You must hold a valid rune in your offhand!"), true);
			return TypedActionResult.fail(staffStack);
		}

		// Determine damage based on staff type
		int fireballDamage = getDamageFromStaff(staffStack.getItem());

		if (!world.isClient) {
			// Consume one rune from offhand
			offhandStack.decrement(1);

			// Fireball launch
			Vec3d dir = user.getRotationVector();

			if (offhandItem == ModItems.EXPLO_RUNE) {
				ExplodingProjectileEntity bomb = new ExplodingProjectileEntity(world, user, dir.x, dir.y, dir.z);
				bomb.setPosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
				world.spawnEntity(bomb);
				user.getItemCooldownManager().set(this, 40);
			} else if (offhandItem == ModItems.WATER_RUNE) {
			int freezeTicks = 10; // default 1 second
			Item staffItem = staffStack.getItem();

			if (staffItem == ModItems.FIREBALL_STAFF_IRON) {
				freezeTicks = 20; // 1 seconds
			} else if (staffItem == ModItems.FIREBALL_STAFF_COPPER) {
				freezeTicks = 60; // 3 seconds
			} else if (staffItem == ModItems.FIREBALL_STAFF_GOLD) {
				freezeTicks = 60; // 3 seconds
			} else if (staffItem == ModItems.FIREBALL_STAFF_DIAMOND) {
				freezeTicks = 80; // 4 seconds
			}

			WaterProjectileEntity projectile = new WaterProjectileEntity(world, user, dir.x, dir.y, dir.z, freezeTicks);
			projectile.setPosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
			world.spawnEntity(projectile);
		} else if (offhandItem == ModItems.EARTH_RUNE) {
				int maxCasts = 0;
				Item staffItem = staffStack.getItem();

				if (staffItem == ModItems.FIREBALL_STAFF_COPPER) {
					maxCasts = 1;
				} else if (staffItem == ModItems.FIREBALL_STAFF_GOLD) {
					maxCasts = 2;
				} else if (staffItem == ModItems.FIREBALL_STAFF_DIAMOND) {
					maxCasts = 3;
				}

				// Count existing resistance effect amplifier
				StatusEffectInstance current = user.getStatusEffect(StatusEffects.RESISTANCE);
				int currentLevel = current != null ? current.getAmplifier() + 1 : 0;

				if (currentLevel >= maxCasts) {
					user.sendMessage(Text.of("You can't boost your armor any further with this staff!"), true);
					return TypedActionResult.fail(staffStack);
				}

				// Apply new resistance with increased level
				user.addStatusEffect(new StatusEffectInstance(
						StatusEffects.RESISTANCE,
						20 * 60 * 2, // 2 minutes
						currentLevel
				));

				offhandStack.decrement(1);
				world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			} else if (offhandItem == ModItems.AIR_RUNE) {
				int range = 3;
				float width = 1f;
				double pushStrength = 0.5;

				Item staffItem = staffStack.getItem();
				if (staffItem == ModItems.FIREBALL_STAFF_COPPER) {
					range = 4;
					pushStrength = 0.8;
				} else if (staffItem == ModItems.FIREBALL_STAFF_GOLD) {
					range = 5;
					pushStrength = 1.2;
				} else if (staffItem == ModItems.FIREBALL_STAFF_DIAMOND) {
					range = 8;
					width = 3f;
					pushStrength = 1.8;
				}

				Vec3d look = user.getRotationVec(1.0F);
				Vec3d origin = user.getPos();

				List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class,
						new Box(origin.add(-range, -range, -range), origin.add(range, range, range)),
						e -> e != user && e.isAlive());

				for (LivingEntity target : entities) {
					Vec3d toTarget = target.getPos().subtract(origin);
					double distance = toTarget.length();
					Vec3d dirToTarget = toTarget.normalize();
					double angle = look.dotProduct(dirToTarget);
					boolean inCone = angle > 0.7 && distance <= range;
					boolean withinWidth = Math.abs(look.crossProduct(dirToTarget).length()) < width;

					if (inCone && withinWidth) {
						WaterProjectileEntity.unfreeze(target);

						// Knockback
						Vec3d knockback = dirToTarget.multiply(pushStrength);
						target.addVelocity(knockback.x, 0.4, knockback.z);
						target.velocityModified = true;
					}
				}

				world.playSound(null, user.getX(), user.getY(), user.getZ(),
						SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 1.0F, 1.5F);

				Vec3d start = user.getEyePos();
				for (int i = 1; i <= range; i++) {
					Vec3d pos = start.add(look.multiply(i));
					((ServerWorld) world).spawnParticles(
							ParticleTypes.CLOUD,
							pos.x, pos.y, pos.z,
							5, 0.1, 0.1, 0.1, 0.01
					);
				}
			}


			else {
				// Fireball
				offhandStack.decrement(1);
				CustomFireballEntity fireball = new CustomFireballEntity(world, user, dir.x, dir.y, dir.z);
				fireball.setPosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
				fireball.setCustomDamage(fireballDamage);
				world.spawnEntity(fireball);
			}

			// Sound and staff damage
			world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
			staffStack.damage(1, user, p -> p.sendToolBreakStatus(hand));
		}

		user.swingHand(hand);
		return TypedActionResult.success(staffStack, world.isClient());
	}
}
