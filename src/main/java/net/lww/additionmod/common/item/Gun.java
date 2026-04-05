package net.lww.additionmod.common.item;

import net.lww.additionmod.AdditionMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import net.minecraft.world.item.Items;

public class Gun extends Item {

    private int fireLevel = 0;

    public Gun(Properties pProperties) {
        super(pProperties);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(
            @NotNull Level pLevel,
            @NotNull Player pPlayer,
            @NotNull InteractionHand pHand
    ) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide && itemstack.getDamageValue() > 0) {
            ArrowItem arrowitem = (ArrowItem) Items.ARROW;
            AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, pPlayer);
            abstractarrow.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 3.0F, 1.0F);
            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + 2.5F);
            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            pLevel.addFreshEntity(abstractarrow);
            itemstack.setDamageValue(itemstack.getDamageValue() - 1);
            pPlayer.getCooldowns().addCooldown(this, 4);
            abstractarrow.setSecondsOnFire(fireLevel * 50);
        }
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) * 1.5F);
        return InteractionResultHolder.success(itemstack);
    }

    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        BlockPos clickedPos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        InteractionHand hand = pContext.getHand();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        ItemStack itemstack = player.getItemInHand(hand);
        boolean success = false;
        if (!level.isClientSide()) {
            if (level.getBlockState(clickedPos) == Blocks.IRON_BLOCK.defaultBlockState() &&
                itemstack.getDamageValue() != itemstack.getMaxDamage()
            ) {
                fireLevel = 0;
                success = level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                itemstack.setDamageValue(itemstack.getMaxDamage());
                player.getCooldowns().addCooldown(this, 20);
            } else if (level.getBlockState(clickedPos) == Blocks.MAGMA_BLOCK.defaultBlockState()) {
                success = level.setBlock(clickedPos, Blocks.AIR.defaultBlockState(), 3);
                fireLevel ++;
            } else {
                Vec3 lookAngle = player.getLookAngle();
                Vec3i vec3i = new Vec3i(((int) lookAngle.x), ((int) lookAngle.y), ((int) lookAngle.z));
                BlockPos pos = clickedPos.cross(vec3i);
                AdditionMod.LOGGER.warn(String.valueOf(pos.getX()), pos.getY(), pos.getZ());
                for (int i = 0; i < 20; i++) {
                    if (level.getBlockState(pos) == Blocks.BEDROCK.defaultBlockState()) {
                        break;
                    }
                    success = level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    pos = clickedPos.cross(vec3i);
                }
            }
        }
        if (success) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PUT,
                    SoundSource.BLOCKS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) * 1.5F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

}
