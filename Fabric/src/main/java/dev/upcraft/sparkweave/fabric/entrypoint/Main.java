package dev.upcraft.sparkweave.fabric.entrypoint;

import dev.upcraft.sparkweave.api.annotation.CalledByReflection;
import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.logging.SparkweaveLoggerFactory;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import dev.upcraft.sparkweave.api.registry.block.BlockItemProvider;
import dev.upcraft.sparkweave.entrypoint.EntrypointHelper;
import dev.upcraft.sparkweave.scheduler.ScheduledTaskQueue;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

@CalledByReflection
public class Main implements ModInitializer {

	@Override
	public void onInitialize() {
		RegistryService.get().visitRegistry(BuiltInRegistries.BLOCK, (id, block) -> {
			if (block instanceof BlockItemProvider provider) {
				Registry.register(BuiltInRegistries.ITEM, id, provider.createItem());
			}
		});

		ServerLifecycleEvents.SERVER_STARTING.register(ScheduledTaskQueue::onServerStarting);
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> ScheduledTaskQueue.onServerStopped());
		ServerTickEvents.START_SERVER_TICK.register(server -> ScheduledTaskQueue.onServerTick());

		EntrypointHelper.fireEntrypoints(MainEntryPoint.class, MainEntryPoint::onInitialize);

		SparkweaveLoggerFactory.getLogger().debug("System initialized!");
	}
}
