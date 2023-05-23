package com.anton.server.commands;




import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.models.City;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Collection;

public class Show extends AbstractCommand{
    private CollectionManager collectionManager;


    public Show( CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;

    }


    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        Collection<City> collection = collectionManager.getCollection();




        if (collection == null || collection.isEmpty()) {
            return new Response(ResponseStatus.ERROR,"Коллекция ещё не инициализирована");
        }
        return new Response(ResponseStatus.OK,"Коллекция: ",collection);
    }
}
