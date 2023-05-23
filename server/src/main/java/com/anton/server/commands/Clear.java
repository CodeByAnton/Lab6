package com.anton.server.commands;


import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

/**
 * Command that clear collection
 */
public class Clear extends AbstractCommand implements CollectionChanger{
    private CollectionManager collectionManager;


    public Clear( CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;

    }


    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        collectionManager.clear();
        return new Response(ResponseStatus.OK,"Коллекция очищена");
    }
}
