package snownee.researchtable.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import snownee.researchtable.ResearchTable;
import snownee.researchtable.core.DataStorage;
import snownee.researchtable.core.Research;
import snownee.researchtable.core.ResearchList;

public class CommandResearch extends CommandBase
{

    @Override
    public String getName()
    {
        return ResearchTable.MODID;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "commands." + getName() + ".usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length != 2 && args.length != 3)
        {
            throw new WrongUsageException(getUsage(sender));
        }
        EntityPlayerMP player = getPlayer(server, sender, args[0]);
        Optional<Research> result = ResearchList.find(args[1]);
        if (!result.isPresent())
        {
            throw new CommandException("commands." + getName() + ".researchNotFound", args[1]);
        }
        Research research = result.get();
        if (args.length == 2)
        {
            notifyCommandListener(sender, this, "commands." + getName() + ".get", player.getName(),
                    DataStorage.count(player.getName(), research));
        }
        else
        {
            int count = parseInt(args[2], 0);
            DataStorage.setCount(player.getName(), research, count);
            notifyCommandListener(sender, this, "commands." + getName() + ".set", player.getName());
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        if (args.length == 2)
        {
            List<String> names = ResearchList.LIST.stream().map(Research::getName).collect(Collectors.toList());
            return getListOfStringsMatchingLastWord(args, names);
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }
}