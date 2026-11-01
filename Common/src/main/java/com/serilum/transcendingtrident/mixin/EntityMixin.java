package com.serilum.transcendingtrident.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.serilum.transcendingtrident.config.ConfigHandler;
import com.serilum.transcendingtrident.util.Util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;

@Mixin(value = Entity.class, priority = 1001)
public class EntityMixin {
	@Inject(method = "isInWaterOrRain()Z", at = @At(value = "RETURN"), cancellable = true)
	private void isInWaterOrRain(CallbackInfoReturnable<Boolean> ci) {
		Entity entity = (Entity)(Object)this;
		if (entity instanceof Player) {
			Player player = (Player)entity;
			if (!(player.getMainHandItem().getItem() instanceof TridentItem) && !(player.getOffhandItem().getItem() instanceof TridentItem)) {
				return;
			}

			if (!Util.canUseRiptideAtCurrentHeight(player)) {
				return;
			}

			if (Util.isHoldingRiptideItem(player) || !ConfigHandler.mustHoldBucketOfWater) {
				ci.setReturnValue(true);
			}
		}
	}
}
