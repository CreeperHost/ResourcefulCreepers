package net.creeperhost.resourcefulcreepers.init;

import net.creeperhost.polylib.registry.PolyRegistry;
import net.creeperhost.resourcefulcreepers.Constants;
import net.creeperhost.resourcefulcreepers.data.CreeperType;
import net.creeperhost.resourcefulcreepers.data.CreeperTypeList;
import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ServerLevelAccessor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModEntities {
    public static final PolyRegistry<EntityType<?>> ENTITIES = PolyRegistry.create(Registries.ENTITY_TYPE, Constants.MOD_ID);
    public static final PolyRegistry<Item> ITEMS = PolyRegistry.create(Registries.ITEM, Constants.MOD_ID);
    public static final Map<CreeperType, Supplier<EntityType<EntityResourcefulCreeper>>> CREEPERS = new LinkedHashMap<>();
    public static final Map<CreeperType, Supplier<Item>> EGGS = new LinkedHashMap<>();
    static {
        for (CreeperType type : CreeperTypeList.INSTANCE.creeperTypes) {
            CREEPERS.put(type, ENTITIES.registerWithKey(type.getName(), key -> EntityType.Builder
                .of(EntityResourcefulCreeper::new, MobCategory.MONSTER).sized(0.6F, 1.7F)
                .clientTrackingRange(8).build(key)));
            EGGS.put(type, ITEMS.registerItem(type.getName(), props -> new SpawnEggItem(props.spawnEgg(CREEPERS.get(type).get())
                    .component(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(List.of(), List.of(), List.of(),
                            List.of(type.getSpawnEggColour1() & 0xffffff, type.getSpawnEggColour2() & 0xffffff)))) {
                @Override public Component getName(ItemStack stack) {
                    return Component.translatable("item.resourcefulcreepers.spawn_egg", type.getDisplayName());
                }
            }));
        }
    }
    public static boolean checkMonsterSpawnRules(EntityType<?> type, ServerLevelAccessor level, EntitySpawnReason reason, BlockPos pos, RandomSource random, CreeperType creeperType) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
        BlockPos below = pos.below();
        if (!level.getBlockState(below).isValidSpawn(level, below, type)) return false;
        return !level.getBiome(pos).is(BiomeTags.IS_OVERWORLD) || Monster.isDarkEnoughToSpawn(level, pos, random);
    }
}
