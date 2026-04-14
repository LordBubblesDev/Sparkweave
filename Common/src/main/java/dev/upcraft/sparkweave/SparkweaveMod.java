package dev.upcraft.sparkweave;

import dev.upcraft.sparkweave.api.entrypoint.MainEntryPoint;
import dev.upcraft.sparkweave.api.platform.ModContainer;
import dev.upcraft.sparkweave.api.platform.services.RegistryService;
import dev.upcraft.sparkweave.registry.SparkweavePlacementModifiers;
import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.stream.Stream;

public class SparkweaveMod implements MainEntryPoint {

	public static final String MODID = "sparkweave";

	@Override
	public void onInitialize(ModContainer mod) {
		var registryService = RegistryService.get();
		SparkweavePlacementModifiers.MODIFIERS.accept(registryService);
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

	public static List<Identifier> ids(String... paths) {
		Validate.notEmpty(paths, "Must provide at least 1 ID!");
		return Stream.of(paths).map(SparkweaveMod::id).toList();
	}
}
