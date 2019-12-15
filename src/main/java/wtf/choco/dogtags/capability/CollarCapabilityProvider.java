package wtf.choco.dogtags.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CollarCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    private final LazyOptional<ICollarCapability> capability = LazyOptional.of(DogTagCapabilities.COLLAR::getDefaultInstance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return (cap == DogTagCapabilities.COLLAR) ? capability.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) DogTagCapabilities.COLLAR.getStorage().writeNBT(DogTagCapabilities.COLLAR, capability.orElseThrow(IllegalStateException::new), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        DogTagCapabilities.COLLAR.getStorage().readNBT(DogTagCapabilities.COLLAR, capability.orElseThrow(IllegalStateException::new), null, nbt);
    }

}
