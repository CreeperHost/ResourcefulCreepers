package net.creeperhost.resourcefulcreepers.mixin;

import net.creeperhost.resourcefulcreepers.entites.EntityResourcefulCreeper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class MixinServerExplosion {
    @ModifyVariable(method = "explode", at = @At("STORE"), ordinal = 0)
    private List<BlockPos> resourcefulcreepers$captureBlocks(List<BlockPos> blocks) {
        if (((ServerExplosion)(Object)this).getDirectSourceEntity() instanceof EntityResourcefulCreeper creeper) {
            creeper.captureExplodedPositions(blocks);
        }
        return blocks;
    }
}
