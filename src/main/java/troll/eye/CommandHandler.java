package troll.eye;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CommandHandler {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {

            registerConsoleAlias(dispatcher, "day", "time set day", "%s changed the time to day.", true);
            registerConsoleAlias(dispatcher, "night", "time set night", "%s changed the time to night.", true);
            registerConsoleAlias(dispatcher, "noon", "time set noon", "%s changed the time to noon.", true);
            registerConsoleAlias(dispatcher, "sun", "weather clear", "%s cleared the weather.", true);
            registerConsoleAlias(dispatcher, "rain", "weather rain", "%s started the rain.", true);

        });
    }

    /**
     * A helper method to easily create custom commands that execute vanilla commands as the server.
     *
     * @param dispatcher     The Brigadier command dispatcher.
     * @param commandName    What the player types (e.g., "day").
     * @param vanillaCommand What the server actually runs secretly (e.g., "time set day").
     * @param successMessage The message template. Use "%s" for the player's name.
     * @param broadcastState      Whether to broadcast the message to server ops.
     */
    private static void registerConsoleAlias(CommandDispatcher<CommandSourceStack> dispatcher, String commandName, String vanillaCommand, String successMessage, boolean broadcastState) {
        dispatcher.register(
                Commands.literal(commandName)
                        .requires(source -> true)
                        .executes(context -> {

                            String executorName = context.getSource().getTextName();

                            context.getSource().getServer().getCommands().performPrefixedCommand(
                                    context.getSource().getServer().createCommandSourceStack().withSuppressedOutput(),
                                    vanillaCommand
                            );

                            String finalMessage = String.format(successMessage, executorName);

                            if (broadcastState) {
                                context.getSource().getServer().getPlayerList().broadcastSystemMessage(
                                        Component.literal(finalMessage),
                                        false
                                );
                            }

                            else {
                                context.getSource().sendSuccess(
                                        () -> Component.literal(finalMessage),
                                        true
                                );
                            }

                            return 1;
                        })
        );
    }
}