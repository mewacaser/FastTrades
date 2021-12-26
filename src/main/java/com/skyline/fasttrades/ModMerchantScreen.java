package com.skyline.fasttrades;

import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.MerchantContainer;
import net.minecraft.inventory.container.MerchantResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.play.client.CSelectTradePacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModMerchantScreen extends MerchantScreen {

	public ModMerchantScreen(MerchantContainer p_i51080_1_, PlayerInventory p_i51080_2_, ITextComponent p_i51080_3_) {
		super(p_i51080_1_, p_i51080_2_, p_i51080_3_);
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
					this.minecraft.getConnection().send(new CSelectTradePacket(this.shopItem));
					count = sell.getItem().getCount();
					this.slotClicked(slot, 2, 0, ClickType.QUICK_MOVE);
				}
			} else {
				this.menu.tryMoveItems(this.shopItem);
				this.minecraft.getConnection().send(new CSelectTradePacket(this.shopItem));
				this.slotClicked(slot, 2, 0, ClickType.PICKUP);
			}
		}

		this.menu.tryMoveItems(this.shopItem);
		this.minecraft.getConnection().send(new CSelectTradePacket(this.shopItem));
	}

	protected void init() {
		super.init();

		for (int l = 0; l < 7; ++l) {
			this.tradeOfferButtons[l].onPress = (button) -> {
				if (button instanceof MerchantScreen.TradeButton) {
					this.shopItem = ((MerchantScreen.TradeButton) button).getIndex() + this.scrollOff;
					this.postButtonClick();
				}

			};
		}
	}
}