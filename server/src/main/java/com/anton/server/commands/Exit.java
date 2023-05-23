package com.anton.server.commands;


import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;

/**
 * Command that exit from program
 */
public class Exit extends AbstractCommand{
    public Exit(){
        super("exit","завершить программу");

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        return new Response(ResponseStatus.EXIT);
    }
}
