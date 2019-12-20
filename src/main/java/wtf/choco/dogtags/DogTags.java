package wtf.choco.dogtags;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import wtf.choco.dogtags.capability.CollarCapability;
import wtf.choco.dogtags.capability.CollarStorage;
import wtf.choco.dogtags.capability.ICollarCapability;
import wtf.choco.dogtags.client.DogTagsClient;
import wtf.choco.dogtags.network.DogTagsPacketHandler;

// https://www.reddit.com/r/minecraftsuggestions/comments/e8jzh9/to_remember_them/

@Mod(DogTags.MOD_ID)
public final class DogTags {

    public static final String MOD_ID = "dogtags";

    public DogTags() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onCommonSetup);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(DogTagsClient::onClientSetup));
    }

    private void onCommonSetup(@SuppressWarnings("unused") FMLCommonSetupEvent event) {
        DogTagsPacketHandler.init();
        CapabilityManager.INSTANCE.register(ICollarCapability.class, new CollarStorage(), CollarCapability::new);
    }

}
