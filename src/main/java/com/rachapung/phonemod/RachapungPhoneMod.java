package com.rachapung.phonemod;

import com.rachapung.phonemod.item.RachapungPhoneItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RachapungPhoneMod implements ModInitializer {
    public static final String MOD_ID = "rachapungphone";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Item RACHAPUNG_PHONE = new RachapungPhoneItem(
            new Item.Settings().maxCount(1)
    );

    @Override
    public void onInitialize() {
        Registry.register(
                Registries.ITEM,
                Identifier.of(MOD_ID, "rachapung_phone"),
                RACHAPUNG_PHONE
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(RACHAPUNG_PHONE);
        });

        LOGGER.info("RACHAPUNG Phone Mod loaded successfully!");
    }
}
