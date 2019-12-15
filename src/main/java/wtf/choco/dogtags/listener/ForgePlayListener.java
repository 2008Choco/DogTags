package wtf.choco.dogtags.listener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.PacketDistributor;

import wtf.choco.dogtags.DogTags;
import wtf.choco.dogtags.capability.CollarCapabilityProvider;
import wtf.choco.dogtags.capability.DogTagCapabilities;
import wtf.choco.dogtags.item.ModItems;
import wtf.choco.dogtags.network.DogTagsPacketHandler;
import wtf.choco.dogtags.network.PacketUpdateCollar;

@EventBusSubscriber(modid = DogTags.MOD_ID)
public class ForgePlayListener {

    @SubscribeEvent
    public static void attachTameableCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof TameableEntity) {
            event.addCapability(new ResourceLocation(DogTags.MOD_ID, "capability_collar"), new CollarCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPetDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
            if (!c.hasCollar()) {
                return;
            }

            ItemStack stack = new ItemStack(ModItems.COLLAR_TAG);
            CompoundNBT nbt = stack.getOrCreateTag();
            nbt.putLong("timestamp", System.currentTimeMillis());
            if (entity.hasCustomName()) {
                nbt.putString("owner", ITextComponent.Serializer.toJson(entity.getCustomName()));
                stack.setDisplayName(entity.getCustomName().appendText("'s Tag"));
            }

            if (entity.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                entity.entityDropItem(stack); 
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerTrackEntity(PlayerEvent.StartTracking event) {
        Entity entity = event.getTarget();
        PlayerEntity player = event.getPlayer();

        if (entity instanceof TameableEntity && player instanceof ServerPlayerEntity) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                DogTagsPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new PacketUpdateCollar(entity, c));
            });
        }
    }

    @SubscribeEvent
    public static void onLoadEntity(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (!entity.world.isRemote && entity instanceof TameableEntity) {
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                DogTagsPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new PacketUpdateCollar(entity, c));
            });
        }
    }

}
