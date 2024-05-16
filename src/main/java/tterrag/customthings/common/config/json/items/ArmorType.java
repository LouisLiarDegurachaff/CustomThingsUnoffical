package tterrag.customthings.common.config.json.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import org.apache.commons.lang3.ArrayUtils;

import com.enderio.core.common.util.ItemUtil;

import cpw.mods.fml.common.registry.GameRegistry;
import lombok.Getter;
import tterrag.customthings.CustomThings;
import tterrag.customthings.common.config.json.IHasMaterial;
import tterrag.customthings.common.item.ItemCustomArmor;

public class ArmorType extends ItemType implements IHasMaterial {

    /* JSON Fields @formatter:off */
    public int[]    armors              = {0, 1, 2, 3};
    public int[]    durabilities        = {100, 400, 500, 150};
    public int[]    protectionDisplays  = {1, 2, 3, 1};
    public double[] protectionRatios    = {0.1, 0.1, 0.1, 0.1};
    public int[]    protectionMaxes     = {5, 15, 20, 7};
    public int[]    priorities          = {0, 0, 0, 0};
    public int      enchantability      = 15;
    public String   material            = "null";
    /* End JSON Fields @formatter:on */

    private transient Item[] items;

    @Getter
    private transient ItemStack repairMat;

    public String getTextureName(int armorType) {
        int num = armorType == 2 ? 2 : 1;
        return name + num;
    }

    public String getMaterialName() {
        return name + "Material";
    }

    public ArmorMaterial getMaterial() {
        return ArmorMaterial.valueOf(getMaterialName());
    }

    private static final String[] names = { "Helm", "Chest", "Legs", "Boots" };
    public static final List<ArmorType> types = new ArrayList<ArmorType>();

    @Override
    public void register() {
        super.register();
        EnumHelper.addArmorMaterial(getMaterialName(), 0, protectionDisplays, enchantability); // dummy, used for
                                                                                               // enchantability
        items = new Item[4];
        for (int i = 0; i <= 3; i++) {
            if (ArrayUtils.contains(armors, i)) {
                items[i] = new ItemCustomArmor(this, i);
                GameRegistry.registerItem(items[i], name + names[i]);
                addOreDictNames(new ItemStack(items[i]));
            }
        }
        types.add(this);
    }

    @Override
    public void postInit() {
        repairMat = material.equals("null") ? null : ItemUtil.parseStringIntoItemStack(material);
    }

    public String getUnlocName(int slot) {
        return name + names[slot];
    }

    public String getIconName(int slot) {
        return CustomThings.MODID.toLowerCase() + ":" + getUnlocName(slot);
    }

    public Item[] getItems() {
        return items;
    }
}
