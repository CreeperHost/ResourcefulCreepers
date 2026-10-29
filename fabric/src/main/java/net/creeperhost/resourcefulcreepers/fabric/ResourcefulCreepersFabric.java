package net.creeperhost.resourcefulcreepers.fabric;

import net.creeperhost.resourcefulcreepers.ResourcefulCreepers;
import net.creeperhost.resourcefulcreepers.ResourcefulCreepersPlatform;
import net.creeperhost.resourcefulcreepers.data.CreeperType;
import net.creeperhost.resourcefulcreepers.data.CreeperTypeList;
import net.creeperhost.resourcefulcreepers.init.ModEntities;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;
import java.util.function.Supplier;

public class ResourcefulCreepersFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        ResourcefulCreepers.init();

        ModEntities.CREEPERS.forEach((type, entity) -> FabricDefaultAttributeRegistry.register(entity.get(), EntityResourcefulCreeper.prepareAttributes(type)));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> ModEntities.EGGS.values().forEach(egg -> entries.accept(egg.get())));
        for (CreeperType creeperType : CreeperTypeList.INSTANCE.creeperTypes) {
            if (creeperType.allowNaturalSpawns()) {
                ResourcefulCreepers.LOGGER.info("registering spawn for {}", creeperType.getDisplayName());
                addSpawn(() -> ModEntities.CREEPERS.get(creeperType).get(), creeperType);
            }
        }
    }

    public static <T extends Animal> void addSpawn(Supplier<EntityType<T>> entityType, CreeperType creeperType) {
        try {
            List<TagKey<Biome>> tags = creeperType.getBiomesTags().stream().map(e -> TagKey.create(Registries.BIOME, Identifier.parse(e))).toList();
            BiomeModifications.addSpawn(e -> tags.stream().anyMatch(e::hasTag), MobCategory.MONSTER, entityType.get(), creeperType.getSpawnWeight(), creeperType.getMinGroup(), creeperType.getMaxGroup());
            SpawnPlacements.register(entityType.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (type, serverLevelAccessor, mobSpawnType, blockPos, randomSource) -> ModEntities.checkMonsterSpawnRules(type, serverLevelAccessor, mobSpawnType, blockPos, randomSource, creeperType));
        } catch (Exception e) {
            ResourcefulCreepers.LOGGER.error("Failed to register creeper type: {}", creeperType.getName(), e);
        }
    }
}
