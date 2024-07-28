package com.natamus.transcendingtrident;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.transcendingtrident.events.TridentEvent;
import com.natamus.transcendingtrident.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		UseItemCallback.EVENT.register((player, world, hand) -> {
			return TridentEvent.onItem(player, world, hand);
		});
	}

	private static void setGlobalConstants() {

	}
}
