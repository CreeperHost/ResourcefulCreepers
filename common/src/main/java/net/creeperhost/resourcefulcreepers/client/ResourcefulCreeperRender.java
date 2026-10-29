package net.creeperhost.resourcefulcreepers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.creeper.CreeperModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class ResourcefulCreeperRender extends MobRenderer<EntityResourcefulCreeper, CreeperRenderState, CreeperModel> {
    public static final Identifier CREEPER_LOCATION = Identifier.withDefaultNamespace("textures/entity/creeper/creeper.png");
    public static class State extends CreeperRenderState { public Identifier texture = CREEPER_LOCATION; }
    public ResourcefulCreeperRender(EntityRendererProvider.Context context) {
        super(context, new CreeperModel(context.bakeLayer(ModelLayers.CREEPER)), 0.5F);
        addLayer(new CreeperPowerLayer(this, context.getModelSet()));
    }
    @Override public State createRenderState() { return new State(); }
    @Override public void extractRenderState(EntityResourcefulCreeper entity, CreeperRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.swelling = entity.getSwelling(partialTicks);
        state.isPowered = entity.isPowered();
        Identifier texture = entity.getCreeperType() == null ? null : ResourcefulCreeperClient.getTexture(entity.getCreeperType().getName());
        ((State) state).texture = texture == null ? CREEPER_LOCATION : texture;
    }
    @Override public Identifier getTextureLocation(CreeperRenderState state) { return ((State) state).texture; }
    @Override protected float getWhiteOverlayProgress(CreeperRenderState state) {
        return (int)(state.swelling * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(state.swelling, 0.5F, 1.0F);
    }
    @Override protected void scale(CreeperRenderState state, PoseStack stack) {
        float swelling = state.swelling;
        float wobble = 1.0F + Mth.sin(swelling * 100.0F) * swelling * 0.01F;
        swelling = Mth.clamp(swelling, 0.0F, 1.0F);
        swelling *= swelling; swelling *= swelling;
        stack.scale((1.0F + swelling * 0.4F) * wobble, (1.0F + swelling * 0.1F) / wobble, (1.0F + swelling * 0.4F) * wobble);
    }
}
