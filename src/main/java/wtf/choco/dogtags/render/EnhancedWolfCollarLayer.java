package wtf.choco.dogtags.render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.ResourceLocation;

import wtf.choco.dogtags.capability.DogTagCapabilities;

public class EnhancedWolfCollarLayer<T extends TameableEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public EnhancedWolfCollarLayer(IEntityRenderer<T, M> renderer) {
        super(renderer);
    }

    @Override // render()
    public void func_225628_a_(MatrixStack stack, IRenderTypeBuffer renderType, int p_225628_3_, T entity, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (entity.isTamed() && !entity.isInvisible()) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                if (!c.hasCollar()) {
                    return;
                }

                int rgb = c.getCollarRGB();
                float r = (rgb >> 16) & 0xFF;
                float g = (rgb >> 8) & 0xFF;
                float b = rgb & 0xFF;

                func_229141_a_(getEntityModel(), WOLF_COLLAR, stack, renderType, p_225628_3_, entity, r / 255.0F, g / 255.0F, b / 255.0F);
            });
        }
    }

}
