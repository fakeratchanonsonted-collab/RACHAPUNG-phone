package com.rachapung.phonemod.item;

import com.rachapung.phonemod.client.RachapungPhoneScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RachapungPhoneItem extends Item {
    public RachapungPhoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            user.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.7f, 1.3f);
            MinecraftClient.getInstance().setScreen(new RachapungPhoneScreen());
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
