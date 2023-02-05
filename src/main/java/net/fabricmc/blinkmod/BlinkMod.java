package net.fabricmc.blinkmod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class BlinkMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("blinkmod");
	private int elapsed = 0;
	private int chance = 0;
	
	@Override
	public void onInitialize() {
		LOGGER.info("Initialized BlinkMod.");

		ServerTickEvents.START_WORLD_TICK.register((idk) -> 
        {
			elapsed++;
			var seconds = elapsed/60;
			if (seconds == 1){chance = 10;}
			if (seconds == 2){chance = 20;}
			if (seconds == 3){chance = 40;}
			if (seconds == 4){chance = 50;}
			if (seconds == 5){chance = 75;}
			if (seconds == 6){elapsed = 0; return;}
			if (elapsed % 60 == 0){
				MinecraftClient mine = MinecraftClient.getInstance();
				MinecraftServer server = null;
				ServerWorld ow = null;
				try{
					server = mine.getServer();
					ow = server.getOverworld();
				}finally{
					if (mine == null || server == null || ow == null) {return;}
				}
				if (chance >= Math.random()*100){
					for (ServerPlayerEntity player : PlayerLookup.world(ow)) {
						var buffpisca = new StatusEffectInstance(StatusEffect.byRawId(15), 20, 0, true, false);
						player.addStatusEffect(buffpisca);
						LOGGER.info("O PLAYER PISCOU, LOL");
						elapsed = 0;
					}
				}
			}
        });
	}
}
