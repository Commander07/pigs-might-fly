package commander.pigsmightfly;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.PlayerInteractedWithEntityCriterion;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(RecipeGenerator::new);
        pack.addProvider(AdvancementsProvider::new);
    }

    private static class RecipeGenerator extends FabricRecipeProvider {
        public RecipeGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        public void generate(RecipeExporter exporter) {
            ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, commander.pigsmightfly.Items.POTION_OF_PIG_FLIGHT).input(Items.DRAGON_BREATH).input(Items.PHANTOM_MEMBRANE).input(Items.FEATHER).input(Items.RABBIT_FOOT).criterion(FabricRecipeProvider.hasItem(commander.pigsmightfly.Items.POTION_OF_PIG_FLIGHT),
                    FabricRecipeProvider.conditionsFromItem(commander.pigsmightfly.Items.POTION_OF_PIG_FLIGHT)).criterion(FabricRecipeProvider.hasItem(Items.DRAGON_BREATH),
                    FabricRecipeProvider.conditionsFromItem(Items.DRAGON_BREATH)).criterion(FabricRecipeProvider.hasItem(Items.PHANTOM_MEMBRANE),
                    FabricRecipeProvider.conditionsFromItem(Items.PHANTOM_MEMBRANE)).criterion(FabricRecipeProvider.hasItem(Items.RABBIT_FOOT),
                    FabricRecipeProvider.conditionsFromItem(Items.RABBIT_FOOT)).offerTo(exporter);
        }
    }

    private static class AdvancementsProvider extends FabricAdvancementProvider {
        protected AdvancementsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(output, registryLookup);
        }

        @Override
        public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
            AdvancementEntry rootAdvancement = Advancement.Builder.create()
                    .display(
                            commander.pigsmightfly.Items.POTION_OF_PIG_FLIGHT,
                            Text.literal("When pigs fly!"),
                            Text.literal("Transform a pig into a flying pig with a Potion of Pig Flight."),
                            Identifier.ofVanilla("textures/gui/advancements/backgrounds/adventure.png"),
                            AdvancementFrame.TASK,
                            true,
                            true,
                            false
                    )
                    // The first string used in criterion is the name referenced by other advancements when they want to have 'requirements'
                    .criterion("transformed_pig", PlayerInteractedWithEntityCriterion.Conditions.create(ItemPredicate.Builder.create().items(commander.pigsmightfly.Items.POTION_OF_PIG_FLIGHT), Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(EntityPredicate.Builder.create().type(EntityType.PIG)))))
                    .build(consumer, PigsMightFly.MOD_ID + "/root");
        }
    }
}

