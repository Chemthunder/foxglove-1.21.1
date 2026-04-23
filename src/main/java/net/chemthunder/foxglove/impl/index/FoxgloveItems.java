package net.chemthunder.foxglove.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.chemthunder.foxglove.impl.Foxglove;
import net.chemthunder.foxglove.impl.component.BarkComponent;
import net.chemthunder.foxglove.impl.item.CharmedBarkItem;
import net.chemthunder.foxglove.impl.item.Debugger;
import net.minecraft.item.Item;

public interface FoxgloveItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Foxglove.MOD_ID);

    Item CHARMED_BARK = ITEMS.register("charmed_bark", CharmedBarkItem::new, new Item.Settings().maxCount(1).component(FoxgloveDataComponents.BARK, BarkComponent.EMPTY));

    Item DEBUGGER = ITEMS.register("debugger", Debugger::new, new Item.Settings().maxCount(1));

    static void init() {}
}