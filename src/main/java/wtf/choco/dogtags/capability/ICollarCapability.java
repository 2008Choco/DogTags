package wtf.choco.dogtags.capability;

import net.minecraft.item.ItemStack;

public interface ICollarCapability {

    public boolean setCollar(ItemStack collar);
    
    public ItemStack getCollar();

    public void setHasCollar(boolean collar);

    public boolean hasCollar();

    public void setCollarRGB(int rgb);

    public int getCollarRGB();

}
