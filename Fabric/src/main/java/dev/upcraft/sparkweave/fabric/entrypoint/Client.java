package dev.upcraft.sparkweave.fabric.entrypoint;

import dev.upcraft.sparkweave.api.annotation.CalledByReflection;
import dev.upcraft.sparkweave.api.client.event.ClientTickEvents;
import dev.upcraft.sparkweave.api.client.event.RegisterLayerDefinitionsEvent;
import dev.upcraft.sparkweave.api.entrypoint.ClientEntryPoint;
import dev.upcraft.sparkweave.entrypoint.EntrypointHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import dev.upcraft.sparkweave.fabric.impl.registry.RegisterLayerDefinitionsEventImpl;

@Environment(EnvType.CLIENT)
@CalledByReflection
public class Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntrypointHelper.fireEntrypoints(ClientEntryPoint.class, ClientEntryPoint::onInitializeClient);
		RegisterLayerDefinitionsEvent.EVENT.invoker().registerModelLayers(new RegisterLayerDefinitionsEventImpl());
		net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.START_CLIENT_TICK.register(client -> ClientTickEvents.START_TICK.invoker().startTick(client));
		net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(client -> ClientTickEvents.END_TICK.invoker().endTick(client));
	}
}
