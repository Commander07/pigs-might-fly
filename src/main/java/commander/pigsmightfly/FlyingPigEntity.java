package commander.pigsmightfly;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingPigEntity extends PigEntity implements Saddleable {
    public FlyingPigEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
        this.stopRiding();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getControllingPassenger() instanceof PlayerEntity playerEntity && this.isAlive()) {
            this.setNoGravity(playerEntity.isHolding(Items.CARROT_ON_A_STICK));
        } else {
            this.setNoGravity(false);
        }
    }

    @Override
    protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        return new Vec3d(0, 1, 1). multiply(0, controllingPlayer.getPitch() / -90, 1);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        float speed = .15F;
        if (!this.isOnGround())
            speed = .3F;
        return speed;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.hasPassengers()) {
            return false;
        }
        return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }
}
