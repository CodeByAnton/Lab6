package com.anton.server.commands;


import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.exceptions.NoSuchIdException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

public class RemoveById extends AbstractCommand implements CollectionChanger {
    private CollectionManager collectionManager;


    public RemoveById( CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;

    }


    @Override
    public Response execute(Request request) throws IllegalArgumentsException, NoSuchIdException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchIdException();
            collectionManager.removeElement(collectionManager.getById(id));
            return new Response(ResponseStatus.OK,"Объект успешно удалён");
        } catch (NoSuchIdException e) {
            return new Response(ResponseStatus.ERROR,"В коллекции нет элемента с таким id");
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS,"id должно быть числом типа int");
        }
    }
}