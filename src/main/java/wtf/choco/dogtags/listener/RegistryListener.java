package wtf.choco.dogtags.listener;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import wtf.choco.dogtags.DogTags;
import wtf.choco.dogtags.item.CollarItem;
import wtf.choco.dogtags.item.CollarTagItem;

@EventBusSubscriber(modid = DogTags.MOD_ID, bus = Bus.MOD)
public class RegistryListener {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
            new CollarItem(),
            new CollarTagItem()
        );
    }

}
