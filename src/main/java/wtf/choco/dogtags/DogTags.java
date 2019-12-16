package wtf.choco.dogtags;

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
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import wtf.choco.dogtags.capability.CollarCapability;
import wtf.choco.dogtags.capability.CollarStorage;
import wtf.choco.dogtags.capability.ICollarCapability;
import wtf.choco.dogtags.item.CollarItemColourRenderer;
import wtf.choco.dogtags.item.ModItems;
import wtf.choco.dogtags.network.DogTagsPacketHandler;
import wtf.choco.dogtags.render.CatCollarData;
import wtf.choco.dogtags.render.CollarData;
import wtf.choco.dogtags.render.EnhancedCollarLayer;
import wtf.choco.dogtags.render.WolfCollarData;

// https://www.reddit.com/r/minecraftsuggestions/comments/e8jzh9/to_remember_them/

@Mod(DogTags.MOD_ID)
public final class DogTags {

    public static final String MOD_ID = "dogtags";

    public DogTags() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(@SuppressWarnings("unused") FMLCommonSetupEvent event) {
        DogTagsPacketHandler.init();
        CapabilityManager.INSTANCE.register(ICollarCapability.class, new CollarStorage(), CollarCapability::new);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = event.getMinecraftSupplier().get();
        
        minecraft.getItemColors().register(new CollarItemColourRenderer(), ModItems.COLLAR);

        EntityRendererManager rendererManager = minecraft.getRenderManager();
        this.addEnhancedRenderer(rendererManager, WolfEntity.class, WolfCollarData::new);
        this.addEnhancedRenderer(rendererManager, CatEntity.class, CatCollarData::new);
    }

    @SuppressWarnings("unchecked")
    private <T extends TameableEntity, M extends EntityModel<T>> void addEnhancedRenderer(EntityRendererManager rendererManager, Class<T> type, Supplier<CollarData<T, M>> collarDataCreator) {
        EntityRenderer<T> renderer = rendererManager.getRenderer(type);
        if (renderer instanceof LivingRenderer) {
            ((LivingRenderer<T, M>) renderer).addLayer(new EnhancedCollarLayer<>((IEntityRenderer<T, M>) renderer, collarDataCreator.get()));
        }
    }

}
