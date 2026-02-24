package net.lww.additionmod.common.item;

import net.lww.additionmod.AdditionMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AdditionMod.MODID);

    public static Map<String, RegistryObject<Item>> items = new HashMap<>();

    public static void register(IEventBus modEventBus) {
        items.put("PLATE", ITEMS.register("plate", () -> new Plate(new Item.Properties())));
        items.put("FLINT", ITEMS.register("flint", () -> new Flint(new Item.Properties().durability(64))));
        ITEMS.register(modEventBus);
    }
}
