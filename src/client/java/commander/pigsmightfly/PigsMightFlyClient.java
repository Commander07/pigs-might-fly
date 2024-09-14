package commander.pigsmightfly;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class PigsMightFlyClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_FLYING_PIG_LAYER = new EntityModelLayer(Identifier.of(PigsMightFly.MOD_ID, "flying_pig"), "main");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(Entities.FLYING_PIG, FlyingPigEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_FLYING_PIG_LAYER, () -> FlyingPigEntityModel.getTexturedModelData(Dilation.NONE));
	}
}