package tterrag.customthings.common.block;

import java.util.ArrayList;

import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.GameRegistry;
import lombok.experimental.Delegate;
import tterrag.customthings.common.config.json.BlockType;
import tterrag.customthings.common.config.json.BlockType.BlockData;
import tterrag.customthings.common.item.ItemBlockCustomSlab;

public class BlockCustomSlab extends BlockSlab implements IBlockCustom {

    private interface Exclusions {

        boolean isOpaqueCube();

        ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune);

        int damageDropped(int metadata);
    }

    private final BlockProxy<BlockCustomSlab> proxy;

    public final BlockCustomSlab doubleslab;

    @Delegate(excludes = Exclusions.class)
    private BlockProxy<BlockCustomSlab> getProxy() {
        return proxy == null ? BlockProxy.<BlockCustomSlab>dummy() : proxy;
    }

    private static int doubleslab_num = 0;

    public BlockCustomSlab(BlockData data) {
        super(false, data.getType().material);
        this.proxy = new BlockProxy<BlockCustomSlab>(this, data, 8) {

            @Override
            public void setType(BlockType type, int meta) {
                super.setType(type, meta);
                doubleslab.setType(type, meta);
            }
        };
        doubleslab = new BlockCustomSlab(this);
        GameRegistry.registerBlock(doubleslab, ItemBlockCustomSlab.class, "doubleslab" + doubleslab_num++, true);
        setStepSound(data.getType().sound);
        setCreativeTab(CreativeTabs.tabBlock);
        useNeighborBrightness = true;
    }

    private BlockCustomSlab(BlockCustomSlab slab) {
        super(
            true,
            slab.getData()
                .getType().material);
        this.proxy = new BlockProxy<BlockCustomSlab>(this, slab.getData(), 8);
        doubleslab = this;
        setStepSound(
            slab.getData()
                .getType().sound);
    }

    @Override
    public String func_150002_b(int p_150002_1_) {
        String name = getType(p_150002_1_).name;
        if (doubleslab == this) {
            name += ".doubleslab";
        }
        return name;
    }

    @Override
    public boolean func_149730_j() {
        return isOpaqueCube();
    }
}
