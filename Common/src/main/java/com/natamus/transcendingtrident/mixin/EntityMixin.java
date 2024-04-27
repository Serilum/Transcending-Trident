package com.natamus.transcendingtrident.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.natamus.collective.functions.PlayerFunctions;
import com.natamus.transcendingtrident.config.ConfigHandler;

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
			if (PlayerFunctions.isHoldingWater(player) || !ConfigHandler.mustHoldBucketOfWater) {
				if (player.getMainHandItem().getItem() instanceof TridentItem || player.getOffhandItem().getItem()  instanceof TridentItem) {
					ci.setReturnValue(true);
				}
			}
		}
	}
}
