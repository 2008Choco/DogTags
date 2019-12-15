package wtf.choco.dogtags.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CollarItemColourRenderer implements IItemColor {

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        Item item = stack.getItem();
        return (tintIndex == 0 && item instanceof CollarItem) ? ((CollarItem) item).getColor(stack) : 0xFFFFFFFF;
    }

}
