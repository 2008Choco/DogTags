package wtf.choco.dogtags.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class DogTagCapabilities {

    @CapabilityInject(ICollarCapability.class)
    public static final Capability<ICollarCapability> COLLAR = null;

}
