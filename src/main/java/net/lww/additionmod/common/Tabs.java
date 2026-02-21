package net.lww.additionmod.common;

import net.lww.additionmod.common.item.Items;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;
import oshi.util.tuples.Pair;

public class Tabs {
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            for (RegistryObject<Item> item : Items.items.values()) {
                event.accept(item);
            }
        }
    }
}
