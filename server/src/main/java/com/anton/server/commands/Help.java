package com.anton.server.commands;


import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CommandManager;

/**
 * Command, that print all available commands and their description
 */
public class Help extends AbstractCommand{
    private CommandManager commandManager;

    public Help(CommandManager commandManager){
        super("help","вывести справку по доступным командам");
        this.commandManager=commandManager;

    }
    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        return new Response(ResponseStatus.OK,
                String.join("\n",commandManager.getCommands()
                        .stream().map(AbstractCommand::toString).toList()));
    }

}
