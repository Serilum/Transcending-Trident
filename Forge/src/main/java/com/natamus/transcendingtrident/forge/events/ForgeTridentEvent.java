package com.natamus.transcendingtrident.forge.events;

import com.natamus.transcendingtrident.events.TridentEvent;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ForgeTridentEvent {
	@SubscribeEvent
	public static void onItem(PlayerInteractEvent.RightClickItem e) {
		if (TridentEvent.onItem(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.FAIL)) {
			e.setCanceled(true);
		}
	}
}