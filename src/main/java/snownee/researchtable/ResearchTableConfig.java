package snownee.researchtable;

import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ResearchTable.MODID)
@Mod.EventBusSubscriber(modid = ResearchTable.MODID)
public final class ResearchTableConfig
{
    private ResearchTableConfig()
    {
        throw new UnsupportedOperationException("No instance for you");
    }

    @SubscribeEvent
    public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(ResearchTable.MODID))
        {
            ConfigManager.sync(ResearchTable.MODID, Config.Type.INSTANCE);
        }
    }

    @Config.Name("RenderLayer")
    public static BlockRenderLayer renderLayer = BlockRenderLayer.CUTOUT_MIPPED;
}