package commander.pigsmightfly.mixin;

import commander.pigsmightfly.Entities;
import commander.pigsmightfly.FlyingPigEntity;
import commander.pigsmightfly.Items;
import commander.pigsmightfly.PigsMightFly;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {
	@Shadow public abstract boolean isSaddled();

	protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		if (player.getStackInHand(hand).isOf(Items.POTION_OF_PIG_FLIGHT) && !this.getType().equals(Entities.FLYING_PIG)) {
			boolean isClient = this.getWorld().isClient;
			if (!isClient) {
				ServerWorld world = this.getServer().getWorld(this.getWorld().getRegistryKey());

				world.playSound(null, this.getBlockPos(), SoundEvents.BLOCK_NOTE_BLOCK_CHIME.value(), SoundCategory.NEUTRAL);
				world.spawnParticles(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 50, 0, 0, 0, 1);

				FlyingPigEntity flyingPigEntity = Entities.FLYING_PIG.create(world);
				flyingPigEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				flyingPigEntity.setAiDisabled(this.isAiDisabled());
				flyingPigEntity.setBaby(this.isBaby());
				if (this.isSaddled()) {
					flyingPigEntity.saddle(null, null);
				}
				if (this.hasCustomName()) {
					flyingPigEntity.setCustomName(this.getCustomName());
					flyingPigEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				flyingPigEntity.setPersistent();
				world.spawnEntity(flyingPigEntity);

				this.discard();
			}
			cir.setReturnValue(ActionResult.success(isClient));
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		return null;
	}
}