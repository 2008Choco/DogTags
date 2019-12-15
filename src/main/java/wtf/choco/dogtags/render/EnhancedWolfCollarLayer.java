package wtf.choco.dogtags.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import wtf.choco.dogtags.capability.DogTagCapabilities;

@OnlyIn(Dist.CLIENT)
public class EnhancedWolfCollarLayer extends LayerRenderer<WolfEntity, WolfModel<WolfEntity>> {

    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public EnhancedWolfCollarLayer(IEntityRenderer<WolfEntity, WolfModel<WolfEntity>> renderer) {
        super(renderer);
    }

    public void render(WolfEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isTamed() && !entity.isInvisible()) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                this.bindTexture(WOLF_COLLAR);

                int rgb = c.getCollarRGB();
                int b = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int r = (rgb >> 16) & 0xFF;

                GlStateManager.color3f(r / 255.0F, g / 255.0F, b / 255.0F);
                this.getEntityModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            });
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

}
