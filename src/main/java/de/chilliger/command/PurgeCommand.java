package de.chilliger.command;

import de.chilliger.Combidlog;
import de.chilliger.utils.Colors;
import de.chilliger.utils.CombiedLogPermission;
import de.chilliger.utils.Messages;
import dev.jorel.commandapi.CommandAPICommand;

public class PurgeCommand {

    /*
    [14:15:49 INFO]: Chilliger_ issued server command: /purge off
[14:15:49 ERROR]: [CommandAPI] Unhandled exception executing '/purge off'
    java.util.ConcurrentModificationException: null
    at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096) ~[?:?]
    at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050) ~[?:?]
    at Combidlog-1.0.jar/de.chilliger.manager.GameManager.stopPurge(GameManager.java:71) ~[Combidlog-1.0.jar:?]
    at Combidlog-1.0.jar/de.chilliger.command.PurgeCommand.lambda$new$0(PurgeCommand.java:17) ~[Combidlog-1.0.jar:?]        at CommandAPI-9.5.1.jar/dev.jorel.commandapi.executors.CommandExecutor.run(CommandExecutor.java:49) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.executors.NormalExecutor.executeWith(NormalExecutor.java:44) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:137) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:124) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:91) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIHandler.lambda$generateCommand$0(CommandAPIHandler.java:261) ~[CommandAPI-9.5.1.jar:?]
    at com.mojang.brigadier.context.ContextChain.runExecutable(ContextChain.java:73) ~[brigadier-1.2.9.jar:?]
    at net.minecraft.commands.execution.tasks.ExecuteCommand.execute(ExecuteCommand.java:30) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.tasks.ExecuteCommand.execute(ExecuteCommand.java:13) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.UnboundEntryAction.lambda$bind$0(UnboundEntryAction.java:8) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.CommandQueueEntry.execute(CommandQueueEntry.java:5) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.ExecutionContext.runCommandQueue(ExecutionContext.java:103) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.executeCommandInContext(Commands.java:454) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:361) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:348) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:343) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.network.ServerGamePacketListenerImpl.performUnsignedChatCommand(ServerGamePacketListenerImpl.java:2226) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.network.ServerGamePacketListenerImpl.lambda$handleChatCommand$15(ServerGamePacketListenerImpl.java:2200) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.TickTask.run(TickTask.java:18) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.util.thread.BlockableEventLoop.doRunTask(BlockableEventLoop.java:151) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.util.thread.ReentrantBlockableEventLoop.doRunTask(ReentrantBlockableEventLoop.java:24) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:1513) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:201) ~[purpur-1.21.jar:1.21-2251-ac6c312]        at net.minecraft.util.thread.BlockableEventLoop.pollTask(BlockableEventLoop.java:125) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.pollTaskInternal(MinecraftServer.java:1491) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.pollTask(MinecraftServer.java:1484) ~[purpur-1.21.jar:1.21-2251-ac6c312]        at net.minecraft.util.thread.BlockableEventLoop.managedBlock(BlockableEventLoop.java:135) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.managedBlock(MinecraftServer.java:1443) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.waitUntilNextTick(MinecraftServer.java:1450) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.runServer(MinecraftServer.java:1295) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.lambda$spin$0(MinecraftServer.java:332) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at java.base/java.lang.Thread.run(Thread.java:1570) ~[?:?]
            [14:15:49 ERROR]: Command exception: /purge off
    java.util.ConcurrentModificationException: null
    at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096) ~[?:?]
    at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050) ~[?:?]
    at Combidlog-1.0.jar/de.chilliger.manager.GameManager.stopPurge(GameManager.java:71) ~[Combidlog-1.0.jar:?]
    at Combidlog-1.0.jar/de.chilliger.command.PurgeCommand.lambda$new$0(PurgeCommand.java:17) ~[Combidlog-1.0.jar:?]        at CommandAPI-9.5.1.jar/dev.jorel.commandapi.executors.CommandExecutor.run(CommandExecutor.java:49) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.executors.NormalExecutor.executeWith(NormalExecutor.java:44) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:137) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:124) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIExecutor.execute(CommandAPIExecutor.java:91) ~[CommandAPI-9.5.1.jar:?]
    at CommandAPI-9.5.1.jar/dev.jorel.commandapi.CommandAPIHandler.lambda$generateCommand$0(CommandAPIHandler.java:261) ~[CommandAPI-9.5.1.jar:?]
    at com.mojang.brigadier.context.ContextChain.runExecutable(ContextChain.java:73) ~[brigadier-1.2.9.jar:?]
    at net.minecraft.commands.execution.tasks.ExecuteCommand.execute(ExecuteCommand.java:30) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.tasks.ExecuteCommand.execute(ExecuteCommand.java:13) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.UnboundEntryAction.lambda$bind$0(UnboundEntryAction.java:8) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.CommandQueueEntry.execute(CommandQueueEntry.java:5) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.execution.ExecutionContext.runCommandQueue(ExecutionContext.java:103) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.executeCommandInContext(Commands.java:454) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:361) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:348) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.commands.Commands.performCommand(Commands.java:343) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.network.ServerGamePacketListenerImpl.performUnsignedChatCommand(ServerGamePacketListenerImpl.java:2226) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.network.ServerGamePacketListenerImpl.lambda$handleChatCommand$15(ServerGamePacketListenerImpl.java:2200) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.TickTask.run(TickTask.java:18) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.util.thread.BlockableEventLoop.doRunTask(BlockableEventLoop.java:151) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.util.thread.ReentrantBlockableEventLoop.doRunTask(ReentrantBlockableEventLoop.java:24) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:1513) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:201) ~[purpur-1.21.jar:1.21-2251-ac6c312]        at net.minecraft.util.thread.BlockableEventLoop.pollTask(BlockableEventLoop.java:125) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.pollTaskInternal(MinecraftServer.java:1491) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.pollTask(MinecraftServer.java:1484) ~[purpur-1.21.jar:1.21-2251-ac6c312]        at net.minecraft.util.thread.BlockableEventLoop.managedBlock(BlockableEventLoop.java:135) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.managedBlock(MinecraftServer.java:1443) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.waitUntilNextTick(MinecraftServer.java:1450) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.runServer(MinecraftServer.java:1295) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at net.minecraft.server.MinecraftServer.lambda$spin$0(MinecraftServer.java:332) ~[purpur-1.21.jar:1.21-2251-ac6c312]
    at java.base/java.lang.Thread.run(Thread.java:1570) ~[?:?]


     */

    public PurgeCommand() {
        new CommandAPICommand("purge")
                .withPermission(CombiedLogPermission.COMMAND_PURGE.toString())
                .withSubcommand(
                        new CommandAPICommand("off")
                                .executes((sender, args) -> {
                                    if (!Combidlog.getGameManager().isPurge()) return;
                                    Combidlog.getGameManager().stopPurge();
                                    sender.sendMessage(Colors.formatComponent(Messages.DISABLE_PURGE));

                                })
                )

                .withSubcommand(
                        new CommandAPICommand("on")
                                .executes((sender, args) -> {
                                    if (Combidlog.getGameManager().isPurge()) return;
                                    Combidlog.getGameManager().startPurge();
                                    sender.sendMessage(Colors.formatComponent(Messages.ENABLE_PURGE));
                                })
                )
                .executes((sender, args) -> {
                    if (!Combidlog.getGameManager().isPurge()) {
                        Combidlog.getGameManager().startPurge();
                        sender.sendMessage(Colors.formatComponent(Messages.ENABLE_PURGE));
                    } else {
                        Combidlog.getGameManager().stopPurge();
                        sender.sendMessage(Colors.formatComponent(Messages.DISABLE_PURGE));
                    }
                })
                .register();


    }
}
