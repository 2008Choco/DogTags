package wtf.choco.dogtags.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import wtf.choco.dogtags.DogTags;
import wtf.choco.dogtags.capability.DogTagCapabilities;
import wtf.choco.dogtags.capability.ICollarCapability;
import wtf.choco.dogtags.network.DogTagsPacketHandler;
import wtf.choco.dogtags.network.PacketUpdateCollar;

public class CollarItem extends Item implements IDyeableArmorItem {

    private static final byte BIT_TAME_SUCCESSFUL = (byte) 7;

    public CollarItem() {
        super(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1));
        this.setRegistryName(DogTags.MOD_ID, "collar");
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (!(target instanceof TameableEntity) || !target.isAlive()) {
            return false;
        }

        TameableEntity entity = (TameableEntity) target;
        if (entity.isTamed() && !entity.isOwner(playerIn)) {
            playerIn.sendStatusMessage(new TranslationTextComponent("item.dogtags.collar.unowned").appendSibling(target.getType().getName()), true);
            return false;
        }

        ICollarCapability capability = target.getCapability(DogTagCapabilities.COLLAR).orElseThrow(IllegalStateException::new);
        if (capability.hasCollar() && capability.getCollarRGB() == ((IDyeableArmorItem) stack.getItem()).getColor(stack) && (!stack.hasDisplayName() || stack.getDisplayName().equals(entity.getCustomName()))) {
            return false;
        }

        if (!entity.world.isRemote) {
            if (!entity.isTamed()) { // Tame if not tamed
                entity.setTamedBy(playerIn);
                entity.getNavigator().clearPath();
                entity.setAttackTarget(null);
                entity.getAISit().setSitting(true);
                entity.setHealth(20.0F);
                entity.world.setEntityState(entity, BIT_TAME_SUCCESSFUL);

                for (int i = 0; i < 7; i++) {
                    double xOffset = entity.world.rand.nextGaussian() * 0.02D;
                    double yOffset = entity.world.rand.nextGaussian() * 0.02D;
                    double zOffset = entity.world.rand.nextGaussian() * 0.02D;
                    entity.world.addParticle(ParticleTypes.HEART, entity.posX + entity.world.rand.nextFloat() * entity.getWidth() * 2.0F - entity.getWidth(), entity.posY + 0.5D + entity.world.rand.nextFloat() * entity.getHeight(), entity.posZ + entity.world.rand.nextFloat() * entity.getWidth() * 2.0F - entity.getWidth(), xOffset, yOffset, zOffset);
                }
            }

            // Drop a collar if one was already present
            ItemStack existingCollar = capability.getCollar();
            if (!existingCollar.isEmpty() && !playerIn.abilities.isCreativeMode) {
                target.entityDropItem(existingCollar);
            }

            // Update collar state for all tracking clients
            capability.setCollar(stack);
            DogTagsPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new PacketUpdateCollar(entity, capability));

            // Modify the entity
            ((TameableEntity) target).enablePersistence();
            if (stack.hasDisplayName()) {
                target.setCustomName(stack.getDisplayName());
            }

            playerIn.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 0.5F, 2F);
        }

        stack.shrink(1);
        return true;
    }

}
