package wtf.choco.dogtags.render;

import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatCollarData implements CollarData<CatEntity, CatModel<CatEntity>> {

    private static final ResourceLocation CAT_COLLAR = new ResourceLocation("textures/entity/cat/cat_collar.png");

    private final CatModel<CatEntity> catModel = new CatModel<>(0.01F);

    @Override
    public ResourceLocation getTextureLocation() {
        return CAT_COLLAR;
    }

    @Override
    public void render(CatModel<CatEntity> rendererModel, CatEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        rendererModel.setModelAttributes(catModel);
        this.catModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTick);
        this.catModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

}
