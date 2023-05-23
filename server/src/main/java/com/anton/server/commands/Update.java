package com.anton.server.commands;




import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.exceptions.NoSuchIdException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Objects;

public class Update extends AbstractCommand implements CollectionChanger{
    private CollectionManager collectionManager;

    public Update(CollectionManager collectionManager){
        super("update"," обновить значение элемента коллекции, id которого равен заданному ");
        this.collectionManager=collectionManager;

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException, NoSuchIdException {
        if (request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try {
            int id = Integer.parseInt(request.getArgs().trim());
            if (!collectionManager.checkExist(id)) throw new NoSuchIdException();
            if (Objects.isNull(request.getObject())) {
                return new Response(ResponseStatus.ASK_OBJECT, "Для команды " + this.getName() + " нужен объект");

            }
            collectionManager.editById(id, request.getObject());
            return new Response(ResponseStatus.OK,"Объект успешно обновлен");
        } catch (NoSuchIdException e) {
            return new Response(ResponseStatus.ERROR, "В коллекции нет элемента с таким id");
        } catch (NumberFormatException e) {
            return new Response(ResponseStatus.WRONG_ARGUMENTS, "id должно быть числом типа int");
        }

    }
}
