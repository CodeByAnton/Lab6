package com.anton.server.utils;


import com.anton.common.exceptions.CommandRuntimeException;
import com.anton.common.exceptions.ExitException;
import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.exceptions.NoSuchCommandException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CommandManager;

public class RequestHandler {
    private CommandManager commandManager;
    public RequestHandler(CommandManager commandManager){

        this.commandManager=commandManager;
    }
    public Response handle(Request request){
        try {
            commandManager.addToHistory(request.getCommandName());
            return commandManager.execute(request);
        }
        catch (IllegalArgumentsException e){
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"Неверное использование аргументов команды");

        }
        catch (CommandRuntimeException e){
            return new Response(ResponseStatus.ERROR,"Ошибка при выполнении программы");
        }
        catch (NoSuchCommandException e){
            return new Response(ResponseStatus.ERROR,"Такой команды нет в списке");
        }
        catch (ExitException e){
            return new Response(ResponseStatus.EXIT);
        }
    }
}
