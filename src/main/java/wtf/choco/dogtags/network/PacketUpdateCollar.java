package wtf.choco.dogtags.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import wtf.choco.dogtags.capability.DogTagCapabilities;
import wtf.choco.dogtags.capability.ICollarCapability;

public class PacketUpdateCollar {

    private final int entityId;
    private final boolean hasCollar;
    private final int collarColour;

    public PacketUpdateCollar(int entityId, boolean hasCollar, int collarColour) {
        this.entityId = entityId;
        this.hasCollar = hasCollar;
        this.collarColour = collarColour;
    }

    public PacketUpdateCollar(Entity entity, boolean hasCollar, int collarColour) {
        this(entity.getEntityId(), hasCollar, collarColour);
    }

    public PacketUpdateCollar(Entity entity, ICollarCapability capability) {
        this(entity.getEntityId(), capability.hasCollar(), capability.getCollarRGB());
    }

    public PacketUpdateCollar(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.hasCollar = buffer.readBoolean();
        this.collarColour = buffer.readInt();
    }

    public int getEntityId() {
        return entityId;
    }

    public boolean isHasCollar() {
        return hasCollar;
    }

    public int getCollarColour() {
        return collarColour;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(entityId);
        buffer.writeBoolean(hasCollar);
        buffer.writeInt(collarColour);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection() != NetworkDirection.PLAY_TO_CLIENT) {
            return;
        }

        context.enqueueWork(() -> {
            Entity entity = Minecraft.getInstance().player.world.getEntityByID(entityId);
            entity.getCapability(DogTagCapabilities.COLLAR).ifPresent(c -> {
                c.setHasCollar(hasCollar);
                c.setCollarRGB(collarColour);
            });
        });

        context.setPacketHandled(true);
    }

}
