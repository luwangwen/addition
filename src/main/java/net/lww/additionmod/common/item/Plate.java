package net.lww.additionmod.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Plate extends Item {
    public Plate(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(
            Level pLevel,
            @NotNull Player pPlayer,
            @NotNull InteractionHand pHand
    ) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        pLevel.playSound(
                pPlayer,
                pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                SoundEvents.ARMOR_STAND_BREAK,
                SoundSource.PLAYERS,
                1.0F, 1.0F
        );
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}
