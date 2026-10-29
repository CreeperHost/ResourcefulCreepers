package net.creeperhost.resourcefulcreepers.neoforge;

import net.creeperhost.resourcefulcreepers.Constants;
import net.creeperhost.polylib.neoforge.registry.NeoPolyRegistry;
import net.creeperhost.resourcefulcreepers.ResourcefulCreepers;
import net.creeperhost.resourcefulcreepers.data.CreeperType;
import net.creeperhost.resourcefulcreepers.data.CreeperTypeList;
import net.creeperhost.resourcefulcreepers.init.ModEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;

@Mod (Constants.MOD_ID)
public class ResourcefulCreepersNeoForge {
    public ResourcefulCreepersNeoForge(IEventBus modBus) {
        ResourcefulCreepers.init();
        NeoPolyRegistry.registerToBus(modBus, Constants.MOD_ID);
        modBus.addListener(this::registerSpawns);
        modBus.addListener(this::registerAttributes);
        modBus.addListener(this::addEggs);

        if (FMLEnvironment.getDist().isClient()) {
            NeoForgeClient.init(modBus);
        }
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntities.CREEPERS.forEach((type, entity) -> event.put(entity.get(), EntityResourcefulCreeper.prepareAttributes(type).build()));
    }
    private void addEggs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) ModEntities.EGGS.values().forEach(egg -> event.accept(egg.get()));
    }
    private void registerSpawns(RegisterSpawnPlacementsEvent event) {
        for (CreeperType creeperType : CreeperTypeList.INSTANCE.creeperTypes) {
            if (creeperType.allowNaturalSpawns()) {
                ResourcefulCreepers.LOGGER.info("registering spawn for {}", creeperType.getDisplayName());
                event.register(ModEntities.CREEPERS.get(creeperType).get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, level, spawnType, blockPos, randomSource) ->  ModEntities.checkMonsterSpawnRules(type, level, spawnType, blockPos, randomSource, creeperType), RegisterSpawnPlacementsEvent.Operation.OR);
            }
        }
    }
}
