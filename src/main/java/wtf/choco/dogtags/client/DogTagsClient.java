package wtf.choco.dogtags.client;

import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.TameableEntity;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import wtf.choco.dogtags.item.CollarItemColourRenderer;
import wtf.choco.dogtags.item.ModItems;
import wtf.choco.dogtags.render.EnhancedCatCollarLayer;
import wtf.choco.dogtags.render.EnhancedWolfCollarLayer;

public class DogTagsClient {

    public static void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = event.getMinecraftSupplier().get();

        minecraft.getItemColors().register(new CollarItemColourRenderer(), ModItems.COLLAR);

        EntityRendererManager rendererManager = minecraft.getRenderManager();
        addEnhancedRenderer(rendererManager, EntityType.WOLF, EnhancedWolfCollarLayer::new);
        addEnhancedRenderer(rendererManager, EntityType.CAT, EnhancedCatCollarLayer::new);
    }

    @SuppressWarnings("unchecked")
    private static <T extends TameableEntity, M extends EntityModel<T>> void addEnhancedRenderer(EntityRendererManager rendererManager, EntityType<T> type, Function<IEntityRenderer<T, M>, LayerRenderer<T, M>> layerRendererCreator) {
        EntityRenderer<T> renderer = (EntityRenderer<T>) rendererManager.renderers.get(type);
        if (renderer instanceof LivingRenderer) {
            ((LivingRenderer<T, M>) renderer).addLayer(layerRendererCreator.apply((IEntityRenderer<T, M>) renderer));
        }
    }

}
