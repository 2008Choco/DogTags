package wtf.choco.dogtags.render;

import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfCollarData implements CollarData<WolfEntity, WolfModel<WolfEntity>> {

    private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    @Override
    public ResourceLocation getTextureLocation() {
        return WOLF_COLLAR;
    }

    @Override
    public void render(WolfModel<WolfEntity> rendererModel, WolfEntity entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        rendererModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    }

}
