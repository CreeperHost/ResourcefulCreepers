package net.creeperhost.resourcefulcreepers.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ColorHelper {
    public static Identifier getItemTexture(ItemStack stack) {
        ItemStackRenderState state = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().updateForTopItem(state, stack, ItemDisplayContext.NONE, Minecraft.getInstance().level, null, 0);
        Material.Baked material = state.pickParticleMaterial(RandomSource.create());
        if (material == null) return null;
        Identifier sprite = material.sprite().contents().name();
        return Identifier.fromNamespaceAndPath(sprite.getNamespace(), "textures/" + sprite.getPath() + ".png");
    }
    public static List<Integer> getColour(ItemStack stack) {
        Identifier location = getItemTexture(stack);
        if (location == null) return null;
        try {
            var resource = Minecraft.getInstance().getResourceManager().getResource(location);
            if (resource.isEmpty()) return null;
            try (var stream = resource.get().open(); NativeImage image = NativeImage.read(stream)) {
                List<Integer> colors = new ArrayList<>();
                for (int y = 0; y < image.getHeight(); y++) for (int x = 0; x < image.getWidth(); x++) {
                    int color = image.getPixel(x, y);
                    if ((color >>> 24) != 0 && !colors.contains(color)) colors.add(color);
                }
                return colors;
            }
        } catch (Exception e) { return null; }
    }
    public static int getRandomColour(ItemStack stack) {
        List<Integer> colors = getColour(stack);
        return colors == null || colors.isEmpty() ? -1 : colors.get(ThreadLocalRandom.current().nextInt(colors.size()));
    }
}
