package com.serilum.transcendingtrident.util;

import com.natamus.collective.functions.ConfigFunctions;
import com.serilum.transcendingtrident.config.ConfigHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Util {
	public static boolean isHoldingRiptideItem(Player player) {
		return isRiptideItem(player.level(), player.getMainHandItem()) || isRiptideItem(player.level(), player.getOffhandItem());
	}

	public static boolean isRiptideItem(Level level, ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return false;
		}

		String itemId = level.registryAccess().lookupOrThrow(Registries.ITEM).getKey(itemStack.getItem()).toString();
		if (!ConfigFunctions.isInIdList(ConfigHandler.itemsThatAllowRiptide, itemId)) {
			return false;
		}

		if (itemStack.is(Items.POTION)) {
			return isWaterBottle(itemStack);
		}
		return true;
	}

	public static boolean isWaterBottle(ItemStack itemStack) {
		PotionContents potionContents = itemStack.get(DataComponents.POTION_CONTENTS);
		if (potionContents == null) {
			return false;
		}
		return potionContents.is(Potions.WATER);
	}

	public static boolean isRiptideTrident(ItemStack itemStack, LivingEntity livingEntity) {
		return EnchantmentHelper.getTridentSpinAttackStrength(itemStack, livingEntity) > 0;
	}

	public static boolean canUseRiptideAtCurrentHeight(Player player) {
		return ConfigHandler.canUseRiptideInTheAir || player.onGround();
	}

	public static boolean isInRealWaterOrRain(Player player) {
		BlockPos feetPos = player.blockPosition();
		BlockPos headPos = BlockPos.containing(player.getX(), player.getBoundingBox().maxY, player.getZ());
		return player.isInWater() || player.level().isRainingAt(feetPos) || player.level().isRainingAt(headPos);
	}

	public static double getRiptidePowerModifier(Player player) {
		if (player.isFallFlying()) {
			return ConfigHandler.tridentRiptideElytraPowerModifier;
		}
		return ConfigHandler.tridentRiptidePowerModifier;
	}

	public static void boostRiptideLaunch(Player player, ItemStack tridentStack) {
		float riptideStrength = EnchantmentHelper.getTridentSpinAttackStrength(tridentStack, player);
		double extraPowerModifier = getRiptidePowerModifier(player) - 1.0;
		Vec3 extraPush = player.getLookAngle().scale(riptideStrength * extraPowerModifier);
		player.push(extraPush.x, extraPush.y, extraPush.z);

		if (player instanceof ServerPlayer serverPlayer) {
			if (shouldUseUpRiptideItem(serverPlayer)) {
				useUpRiptideItem(serverPlayer);
			}
			syncLaunchToClient(serverPlayer);
		}
	}

	public static boolean shouldUseUpRiptideItem(Player player) {
		if (!ConfigHandler.riptideUsesUpTheItem || !ConfigHandler.mustHoldBucketOfWater) {
			return false;
		}
		return !isInRealWaterOrRain(player);
	}

	public static void useUpRiptideItem(Player player) {
		if (player.getAbilities().instabuild) {
			return;
		}

		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack handStack = player.getItemInHand(hand);
			if (!isRiptideItem(player.level(), handStack)) {
				continue;
			}

			ItemStack emptyContainer = getEmptyContainer(handStack);
			if (emptyContainer.isEmpty()) {
				handStack.shrink(1);
			}
			else {
				player.setItemInHand(hand, ItemUtils.createFilledResult(handStack, player, emptyContainer, false));
			}
			return;
		}
	}

	public static ItemStack getEmptyContainer(ItemStack itemStack) {
		if (itemStack.getItem() instanceof BucketItem) {
			return new ItemStack(Items.BUCKET);
		}
		if (itemStack.is(Items.POTION)) {
			return new ItemStack(Items.GLASS_BOTTLE);
		}
		return ItemStack.EMPTY;
	}

	public static void syncLaunchToClient(ServerPlayer serverPlayer) {
		if (serverPlayer.level().getServer().isSingleplayerOwner(serverPlayer.nameAndId())) {
			return;
		}
		serverPlayer.hurtMarked = true;
	}

	public static void resyncInventory(Player player) {
		if (player instanceof ServerPlayer) {
			player.containerMenu.sendAllDataToRemote();
		}
	}
}
