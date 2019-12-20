package wtf.choco.dogtags.client;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import wtf.choco.dogtags.item.CollarItemColourRenderer;
import wtf.choco.dogtags.item.ModItems;
import wtf.choco.dogtags.render.CatCollarData;
import wtf.choco.dogtags.render.CollarData;
import wtf.choco.dogtags.render.EnhancedCollarLayer;
import wtf.choco.dogtags.render.WolfCollarData;

public class DogTagsClient {
    
    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = event.getMinecraftSupplier().get();
        
        minecraft.getItemColors().register(new CollarItemColourRenderer(), ModItems.COLLAR);

        EntityRendererManager rendererManager = minecraft.getRenderManager();
        addEnhancedRenderer(rendererManager, WolfEntity.class, WolfCollarData::new);
        addEnhancedRenderer(rendererManager, CatEntity.class, CatCollarData::new);
    }

    @SuppressWarnings("unchecked")
    private static <T extends TameableEntity, M extends EntityModel<T>> void addEnhancedRenderer(EntityRendererManager rendererManager, Class<T> type, Supplier<CollarData<T, M>> collarDataCreator) {
        EntityRenderer<T> renderer = rendererManager.getRenderer(type);
        if (renderer instanceof LivingRenderer) {
            ((LivingRenderer<T, M>) renderer).addLayer(new EnhancedCollarLayer<>((IEntityRenderer<T, M>) renderer, collarDataCreator.get()));
        }
    }

}
