package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.item.CharmedBarkItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

public interface FoxgloveItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Foxglove.MOD_ID);

    Item CHARMED_BARK = ITEMS.register("charmed_bark", CharmedBarkItem::new, new Item.Settings().maxCount(16).component(FoxgloveDataComponents.BARK, BarkComponent.EMPTY));

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(FoxgloveItems::buildItemGroupModifier);
    }

    private static void buildItemGroupModifier(FabricItemGroupEntries entries) {
        entries.addAfter(Items.NAME_TAG, CHARMED_BARK);
    }
}