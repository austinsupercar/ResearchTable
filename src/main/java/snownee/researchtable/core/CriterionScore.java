package snownee.researchtable.core;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CriterionScore implements ICriterion
{
    private final String s;
    private final int min;
    private final int max;
    private final String failingText;

    public CriterionScore(String score, int min, int max, String failingText)
    {
        this.min = min;
        this.max = max;
        this.s = score;
        this.failingText = failingText;
    }

    @Override
    public boolean matches(EntityPlayer player, NBTTagCompound data)
    {
        Scoreboard scoreboard = player.world.getScoreboard();
        ScoreObjective scoreobjective = scoreboard.getObjective(s);
        if (scoreobjective == null)
        {
            return false;
        }

        // String key = player instanceof EntityPlayerMP ? player.getName() : player.getCachedUniqueIdString();
        String key = player.getName();
        if (!scoreboard.entityHasObjective(key, scoreobjective))
        {
            return false;
        }

        Score score = scoreboard.getOrCreateScore(key, scoreobjective);
        int i = score.getScorePoints();

        return i >= min && i <= max;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getFailingText(EntityPlayer player, NBTTagCompound data)
    {
        return I18n.format(failingText);
    }

}
