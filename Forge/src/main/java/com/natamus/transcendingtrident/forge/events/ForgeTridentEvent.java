package com.natamus.transcendingtrident.forge.events;

import com.natamus.transcendingtrident.events.TridentEvent;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;


public class ForgeTridentEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeTridentEvent.class);

		PlayerInteractEvent.RightClickItem.BUS.addListener(ForgeTridentEvent::onItem);
	}

	@SubscribeEvent
	public static boolean onItem(PlayerInteractEvent.RightClickItem e) {
		if (TridentEvent.onItem(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.FAIL)) {
			return true;
		}
		return false;
	}
}