package com.skyline.fasttrades;

import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModMerchantScreen extends MerchantScreen {

	public ModMerchantScreen(MerchantMenu p_99123_, Inventory p_99124_, Component p_99125_) {
		super(p_99123_, p_99124_, p_99125_);
	}

	private void postButtonClick() {
		this.menu.setSelectionHint(this.shopItem);
		boolean ctrl = hasControlDown();
		if (ctrl) {
			boolean shift = hasShiftDown();
			MerchantResultSlot slot = (MerchantResultSlot) menu.getSlot(2);
			if (shift) {
				Slot sell = (Slot) menu.getSlot(0);
				int count = -1;
				while (count != (count = sell.getItem().getCount())) {
					this.menu.tryMoveItems(this.shopItem);
					this.minecraft.getConnection().send(new ServerboundSelectTradePacket(this.shopItem));
					count = sell.getItem().getCount();
					this.slotClicked(slot, 2, 0, ClickType.QUICK_MOVE);
				}
			} else {
				this.menu.tryMoveItems(this.shopItem);
				this.minecraft.getConnection().send(new ServerboundSelectTradePacket(this.shopItem));
				this.slotClicked(slot, 2, 0, ClickType.PICKUP);
			}
		}

		this.menu.tryMoveItems(this.shopItem);
		this.minecraft.getConnection().send(new ServerboundSelectTradePacket(this.shopItem));
	}

	protected void init() {
		super.init();

		for (int l = 0; l < 7; ++l) {
			this.tradeOfferButtons[l].onPress = (p_99174_) -> {
				if (p_99174_ instanceof MerchantScreen.TradeOfferButton) {
					this.shopItem = ((MerchantScreen.TradeOfferButton) p_99174_).getIndex() + this.scrollOff;
					this.postButtonClick();
				}

			};
		}
	}
}