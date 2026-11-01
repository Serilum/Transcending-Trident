package com.serilum.transcendingtrident.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.serilum.transcendingtrident.config.ConfigHandler;
import com.serilum.transcendingtrident.util.Util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;

@Mixin(value = TridentItem.class, priority = 1001)
public class TridentItemMixin {
	@ModifyConstant(method = "releaseUsing", constant = @Constant(intValue = 10))
	private int TridentItem_releaseUsing_useDuration(int useDuration, ItemStack itemStack, Level level, LivingEntity livingEntity, int remainingTime) {
		if (!Util.isRiptideTrident(itemStack, livingEntity)) {
			return useDuration;
		}
		return ConfigHandler.tridentRiptideUseDuration;
	}

	@Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;startAutoSpinAttack(IFLnet/minecraft/world/item/ItemStack;)V"))
	private void TridentItem_releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int remainingTime, CallbackInfoReturnable<Boolean> cir) {
		Util.boostRiptideLaunch((Player)livingEntity, itemStack);
	}
}
