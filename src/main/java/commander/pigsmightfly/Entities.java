package commander.pigsmightfly;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static final EntityType<FlyingPigEntity> FLYING_PIG = register(
            EntityType.Builder.create(FlyingPigEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 0.9F).passengerAttachments(0.86875F).maxTrackingRange(10),
            "flying_pig"
    );

    public static <T extends Entity> EntityType<T> register(EntityType.Builder<T> entity, String id) {
        Identifier entityID = Identifier.of(PigsMightFly.MOD_ID, id);
        return Registry.register(Registries.ENTITY_TYPE, entityID, entity.build(id));
    }

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(FLYING_PIG, FlyingPigEntity.createPigAttributes());
    }
}
