package tterrag.customthings.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.experimental.Delegate;
import tterrag.customthings.CustomThings;
import tterrag.customthings.common.config.json.items.RecordType;

public class ItemCustomRecord extends ItemRecord implements ICustomItem<RecordType> {

    @Delegate
    private final ItemProxy<RecordType, ItemCustomRecord> proxy = new ItemProxy<RecordType, ItemCustomRecord>(this);

    private RecordType type;

    public ItemCustomRecord(RecordType recordType) {
        super(CustomThings.MODID + "." + recordType.name);
        this.type = recordType;

        setUnlocalizedName(CustomThings.MODID + ".record." + recordType.name);
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    @Override
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[2];

        icons[0] = register.registerIcon(CustomThings.MODID + ":record_base");
        icons[1] = register.registerIcon(CustomThings.MODID + ":record_color");
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
        return icons[pass % icons.length];
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int pass) {
        return pass == 0 ? 0xFFFFFF : RecordType.getColor(stack.getItem());
    }

    @Override
    public RecordType getType(ItemStack stack) {
        return type;
    }
}
