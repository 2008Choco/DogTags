package wtf.choco.dogtags.item;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import wtf.choco.dogtags.DogTags;

public class CollarTagItem extends Item {

    public CollarTagItem() {
        super(new Item.Properties().maxStackSize(1));
        this.setRegistryName(DogTags.MOD_ID, "collar_tag");
    }

    @Override
    @OnlyIn(Dist.CLIENT) // Must use because of ITooltipFlag
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!stack.hasTag()) {
            return;
        }

        CompoundNBT tag = stack.getTag();
        if (tag.contains("owner")) {
            tooltip.add(new TranslationTextComponent("item.dogtags.collar_tag.memorium").appendSibling(ITextComponent.Serializer.fromJson(tag.getString("owner"))));
        } else {
            tooltip.add(new TranslationTextComponent("item.dogtags.collar_tag.memorium_unnamed"));
        }

        if (tag.contains("timestamp")) {
            LocalDate date = Instant.ofEpochMilli(tag.getLong("timestamp")).atZone(ZoneId.systemDefault()).toLocalDate();
            tooltip.add(new TranslationTextComponent("item.dogtags.collar_tag.date", date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

}
