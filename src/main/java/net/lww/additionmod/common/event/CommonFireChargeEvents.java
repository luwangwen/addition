package net.lww.additionmod.common.event;

import net.lww.additionmod.AdditionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdditionMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonFireChargeEvents {

    @SubscribeEvent
    public static void onUseFireCharge(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        BlockPos pos = player.getOnPos();
        Level level = player.level();
        ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
        AdditionMod.LOGGER.warn("514");
        if (!itemStack.is(Items.FIRE_CHARGE)) {
            return;
        }
        AdditionMod.LOGGER.warn("1919");
        Vec3 vec3 = player.getViewVector(1.0F);
        double d2 = vec3.x * 5.0D;
        double d3 = vec3.y * 5.0D;
        double d4 = vec3.z * 5.0D;
        LargeFireball fireball = new LargeFireball(level, player, d2, d3, d4, 1);
        fireball.setPos(player.getX(), player.getY(0.5D), player.getZ());
        level.addFreshEntity(fireball);
        RandomSource randomsource = level.getRandom();
        level.playSound(player, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F,
                (randomsource.nextFloat() - randomsource.nextFloat()) * 0.2F + 1.0F);
        player.getItemInHand(player.getUsedItemHand()).shrink(1);
        event.setResult(Event.Result.ALLOW);
        AdditionMod.LOGGER.warn("810");
    }

    @SubscribeEvent
    public static void OnUseLightningBolt(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos blockPos = event.getPos();
        Level level = player.level();
        ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
        AdditionMod.LOGGER.warn("514");
        if (!itemStack.is(Items.LIGHTNING_ROD)) {
            return;
        }
        if (level.isThundering()) {
            if (level.canSeeSky(blockPos)) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
                if (lightningbolt != null) {
                    lightningbolt.moveTo(Vec3.atBottomCenterOf(blockPos.above()));
                    lightningbolt.setCause((ServerPlayer) player);
                    level.addFreshEntity(lightningbolt);
                }
                level.playSound(player, blockPos, SoundEvents.TRIDENT_THUNDER, SoundSource.WEATHER, 5.0F, 1.0F);
            }
        }
        event.setCanceled(true);
        AdditionMod.LOGGER.warn("114");
    }

}
