package com.anton.server.commands;


import com.anton.common.exceptions.CommandRuntimeException;
import com.anton.common.exceptions.ExitException;
import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;

public interface Executable {

    Response execute(Request request) throws CommandRuntimeException, ExitException, IllegalArgumentsException;

}
