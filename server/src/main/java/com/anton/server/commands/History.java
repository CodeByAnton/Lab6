package com.anton.server.commands;



import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CommandManager;

import java.util.List;

/**
 * Command that print last 14 command, without their arguments
 */
public class History extends AbstractCommand{
    private CommandManager commandManager;

    public History(CommandManager commandManager){
        super("history","вывести последние 14 команд без их аргуметов ");
        this.commandManager=commandManager;

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        List<String> history=commandManager.getCommandHistory();
        return new Response(ResponseStatus.OK,
                String.join("\n",
                        history.subList(Math.max(history.size()-14,0),history.size())));
    }
}
