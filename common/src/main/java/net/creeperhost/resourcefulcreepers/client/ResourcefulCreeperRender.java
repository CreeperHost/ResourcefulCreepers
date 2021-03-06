package net.creeperhost.resourcefulcreepers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.resourcefulcreepers.Constants;
import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class ResourcefulCreeperRender extends MobRenderer<EntityResourcefulCreeper, ResourcefulCreeperModel<EntityResourcefulCreeper>> implements RenderLayerParent<EntityResourcefulCreeper, ResourcefulCreeperModel<EntityResourcefulCreeper>>
{
    public static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper.png");

    public ResourcefulCreeperRender(EntityRendererProvider.Context context)
    {
        super(context, new ResourcefulCreeperModel<>(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
        this.addLayer(new ResourcefulCreeperPowerLayer(this, context.getModelSet()));
    }

    @Override
    public float getWhiteOverlayProgress(EntityResourcefulCreeper creeper, float f)
    {
        float g = creeper.getSwelling(f);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(g, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityResourcefulCreeper entity)
    {
       String resourceName = entity.getCreeperType().getName();
       if (ResourcefulCreeperClient.getTexture(resourceName) == null) return CREEPER_LOCATION;
       return new ResourceLocation(Constants.MOD_ID, "/textures/entities/" + entity.getCreeperType().getName());
    }

    @Override
    public void render(EntityResourcefulCreeper mob, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i)
    {
        super.render(mob, f, g, poseStack, multiBufferSource, i);
    }
}
