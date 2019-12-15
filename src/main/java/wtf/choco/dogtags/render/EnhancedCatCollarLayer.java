package wtf.choco.dogtags.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import wtf.choco.dogtags.capability.DogTagCapabilities;

@OnlyIn(Dist.CLIENT)
public class EnhancedCatCollarLayer extends LayerRenderer<CatEntity, CatModel<CatEntity>> {

    private static final ResourceLocation CAT_COLLAR = new ResourceLocation("textures/entity/cat/cat_collar.png");

    private final CatModel<CatEntity> catModel = new CatModel<>(0.01F);

    public EnhancedCatCollarLayer(IEntityRenderer<CatEntity, CatModel<CatEntity>> renderer) {
        super(renderer);
    }

    public void render(CatEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isTamed() && !entity.isInvisible()) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                this.bindTexture(CAT_COLLAR);

                int rgb = c.getCollarRGB();
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                GlStateManager.color3f(red / 255.0F, green / 255.0F, blue / 255.0F);

                this.getEntityModel().setModelAttributes(catModel);
                this.catModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTick);
                this.catModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            });
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

}
