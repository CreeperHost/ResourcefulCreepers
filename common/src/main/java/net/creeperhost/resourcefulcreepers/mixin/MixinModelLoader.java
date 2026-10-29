package net.creeperhost.resourcefulcreepers.mixin;

import net.creeperhost.resourcefulcreepers.Constants;
import net.creeperhost.resourcefulcreepers.init.ModEntities;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelManager.class)
public abstract class MixinModelLoader {
    @Shadow public abstract ItemModel getItemModel(Identifier id);
    @Inject(method = "getItemModel", at = @At("HEAD"), cancellable = true)
    private void resourcefulcreepers$spawnEggModel(Identifier id, CallbackInfoReturnable<ItemModel> callback) {
        if (id.getNamespace().equals(Constants.MOD_ID) && !id.getPath().equals("spawn_egg")
                && ModEntities.EGGS.keySet().stream().anyMatch(type -> type.getName().equals(id.getPath()))) {
            callback.setReturnValue(getItemModel(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "spawn_egg")));
        }
    }
}
