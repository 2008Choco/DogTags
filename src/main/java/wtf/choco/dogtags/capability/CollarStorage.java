package wtf.choco.dogtags.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CollarStorage implements IStorage<ICollarCapability> {

    @Override
    public INBT writeNBT(Capability<ICollarCapability> capability, ICollarCapability instance, Direction side) {
        CompoundNBT root = new CompoundNBT();
        root.putBoolean("collared", instance.hasCollar());
        root.putInt("collarColour", instance.getCollarRGB());
        return root;
    }

    @Override
    public void readNBT(Capability<ICollarCapability> capability, ICollarCapability instance, Direction side, INBT nbt) {
        if (!(nbt instanceof CompoundNBT)) {
            return;
        }

        CompoundNBT root = (CompoundNBT) nbt;
        if (root.contains("collared")) {
            instance.setHasCollar(root.getBoolean("collared"));
        }
        if (root.contains("collarColour")) {
            instance.setCollarRGB(root.getInt("collarColour"));
        }
    }

}
