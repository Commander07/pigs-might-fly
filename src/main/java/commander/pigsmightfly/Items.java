package commander.pigsmightfly;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class Items {
    public static final Item POTION_OF_PIG_FLIGHT = register(
            new Item(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)),
            "potion_of_pig_flight"
    );

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(PigsMightFly.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register((itemGroup) -> itemGroup.add(POTION_OF_PIG_FLIGHT));
    }
}
