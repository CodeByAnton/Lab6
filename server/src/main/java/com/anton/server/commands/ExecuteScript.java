package com.anton.server.commands;


import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;

/**
 * Command for execute script from file
 */
public class ExecuteScript extends AbstractCommand {



    public ExecuteScript() {
        super("execute_script", "file_name - считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");

    }

    /**
     * Execute script
     * @param request client
     * @throws IllegalArgumentException
     */

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentException();
        return new Response(ResponseStatus.EXECUTE_SCRIPT,request.getArgs());
    }
}





