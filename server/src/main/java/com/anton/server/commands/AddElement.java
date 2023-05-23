package com.anton.server.commands;




import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Objects;

/**
 * Command that create new City element and add his in to collection{@link com.anton.common.models.City}
 */
public class AddElement extends AbstractCommand implements CollectionChanger{
    private final CollectionManager collectionManager;

    public AddElement(CollectionManager collectionManager){
        super("add","добавить новый элемент в коллекцию");
        this.collectionManager=collectionManager;

    }

    @Override
    public Response execute(Request request) throws  IllegalArgumentException{
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        if (Objects.isNull(request.getObject())){
            return new Response(ResponseStatus.ASK_OBJECT,"Введи объект для следующей команды: "+this.getName());

        } else {
            request.getObject().setId(CollectionManager.getNextId());
            collectionManager.addElement(request.getObject());
            return new Response(ResponseStatus.OK,"Объект успешно добавлен");

        }
    }
}
