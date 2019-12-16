package wtf.choco.dogtags.render;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface CollarData<T extends TameableEntity, M extends EntityModel<T>> {

    public ResourceLocation getTextureLocation();

    public void render(M rendererModel, T entity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch, float scale);

}
