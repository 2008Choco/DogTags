package wtf.choco.dogtags.capability;

import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;

import wtf.choco.dogtags.item.ModItems;

public class CollarCapability implements ICollarCapability {

    private static final int DEFAULT_COLLAR_COLOUR = DyeColor.RED.getColorValue();

    private boolean collar = false;
    private int collarRGB = DEFAULT_COLLAR_COLOUR;

    @Override
    public boolean setCollar(ItemStack collar) {
        if (collar == null || collar.isEmpty()) {
            this.setHasCollar(false);
            this.setCollarRGB(DEFAULT_COLLAR_COLOUR);
            return true;
        }

        if (collar.getItem() == ModItems.COLLAR) {
            this.setHasCollar(true);
            this.setCollarRGB(((IDyeableArmorItem) ModItems.COLLAR).getColor(collar));
            return true;
        }

        return false;
    }
    
    @Override
    public ItemStack getCollar() {
        if (!collar) {
            return ItemStack.EMPTY;
        }
        
        ItemStack stack = new ItemStack(ModItems.COLLAR);
        ((IDyeableArmorItem) ModItems.COLLAR).setColor(stack, collarRGB);
        return stack;
    }

    @Override
    public void setHasCollar(boolean collar) {
        this.collar = collar;
    }

    @Override
    public boolean hasCollar() {
        return collar;
    }

    @Override
    public void setCollarRGB(int rgb) {
        this.collarRGB = rgb;
    }

    @Override
    public int getCollarRGB() {
        return collarRGB;
    }

}
