package net.creeperhost.resourcefulcreepers.client;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.monster.creeper.CreeperModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;

public class ResourcefulCreeperPowerLayer extends CreeperPowerLayer {
    public ResourcefulCreeperPowerLayer(RenderLayerParent<CreeperRenderState, CreeperModel> parent, EntityModelSet models) { super(parent, models); }
}
