package com.serilum.transcendingtrident.events;

import com.serilum.transcendingtrident.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;


public class TridentEvent {
	public static InteractionResult onItem(Player player, Level world, InteractionHand hand) {
		ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
		ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
		if (!mainhand.getItem().equals(Items.TRIDENT)) {
			if (!offhand.getItem().equals(Items.TRIDENT)) {
				return InteractionResult.PASS;
			}
			else if (EnchantmentHelper.getTridentSpinAttackStrength(offhand, player) <= 0) {
				return InteractionResult.PASS;
			}
		}
		else if (EnchantmentHelper.getTridentSpinAttackStrength(mainhand, player) <= 0) {
			return InteractionResult.PASS;
		}

		if (Util.isRiptideItem(world, mainhand)) {
			if (hand.equals(InteractionHand.MAIN_HAND)) {
				Util.resyncInventory(player);
				return InteractionResult.FAIL;
			}
		}
		else if (Util.isRiptideItem(world, offhand)) {
			if (hand.equals(InteractionHand.OFF_HAND)) {
				Util.resyncInventory(player);
				return InteractionResult.FAIL;
			}
		}

		return InteractionResult.PASS;
	}
}
