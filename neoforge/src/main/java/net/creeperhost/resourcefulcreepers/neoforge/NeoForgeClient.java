package net.creeperhost.resourcefulcreepers.neoforge;

import net.creeperhost.resourcefulcreepers.client.ResourcefulCreeperRender;
import net.creeperhost.resourcefulcreepers.init.ModEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class NeoForgeClient {
    public static void init(IEventBus modBus) { modBus.addListener(NeoForgeClient::registerRenderers); }
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ModEntities.CREEPERS.values().forEach(entity -> event.registerEntityRenderer(entity.get(), ResourcefulCreeperRender::new));
    }
}
