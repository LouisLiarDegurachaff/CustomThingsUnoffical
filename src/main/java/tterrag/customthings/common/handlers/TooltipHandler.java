package tterrag.customthings.common.handlers;

import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import com.enderio.core.common.Handlers.Handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import tterrag.customthings.common.item.ICustomItem;

@Handler
public class TooltipHandler {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (event.itemStack != null && event.itemStack.getItem() instanceof ICustomItem<?>) {
            String unloc = event.itemStack.getUnlocalizedName() + ".tooltip.line";
            int lineNum = 1;
            while (StatCollector.canTranslate(unloc + lineNum)) {
                event.toolTip.add(StatCollector.translateToLocal(unloc + lineNum));
                lineNum++;
            }
        }
    }
}
