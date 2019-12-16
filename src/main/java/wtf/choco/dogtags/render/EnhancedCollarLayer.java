package wtf.choco.dogtags.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import wtf.choco.dogtags.capability.DogTagCapabilities;

@OnlyIn(Dist.CLIENT)
public class EnhancedCollarLayer<T extends TameableEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private final CollarData<T, M> collarData;

    public EnhancedCollarLayer(IEntityRenderer<T, M> renderer, CollarData<T, M> collarData) {
        super(renderer);
        this.collarData = collarData;
    }

    @Override
    public void render(T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isTamed() && !entity.isInvisible()) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                this.bindTexture(collarData.getTextureLocation());

                int rgb = c.getCollarRGB();
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                GlStateManager.color3f(r / 255.0F, g / 255.0F, b / 255.0F);
                this.collarData.render(getEntityModel(), entity, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch, scale);
            });
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

}
