package com.natamus.transcendingtrident.neoforge.events;

import com.natamus.transcendingtrident.events.TridentEvent;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;


@EventBusSubscriber
public class NeoForgeTridentEvent {
	@SubscribeEvent
	public static void onItem(PlayerInteractEvent.RightClickItem e) {
		if (TridentEvent.onItem(e.getEntity(), e.getLevel(), e.getHand()).getResult().equals(InteractionResult.FAIL)) {
			e.setCanceled(true);
		}
	}
}