package com.skyline.fasttrades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FastTrades.MODID)
public class FastTrades {
	public static final String MODID = "fasttrades";

	private static final Logger log = LogManager.getLogger();

	public FastTrades() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		try {
			ScreenManager.register(ContainerType.MERCHANT, ModMerchantScreen::new);
			log.info("Didn't register ModMerchantScreen?");
		} catch (IllegalStateException ex) {
			log.info("Registered ModMerchantScreen");
		}
	}
}
