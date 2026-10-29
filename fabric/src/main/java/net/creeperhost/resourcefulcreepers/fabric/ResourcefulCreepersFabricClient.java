package net.creeperhost.resourcefulcreepers.fabric;

import net.creeperhost.resourcefulcreepers.client.ResourcefulCreeperRender;
import net.creeperhost.resourcefulcreepers.init.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ResourcefulCreepersFabricClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        ModEntities.CREEPERS.values().forEach(entity -> EntityRendererRegistry.register(entity.get(), ResourcefulCreeperRender::new));
    }
}
