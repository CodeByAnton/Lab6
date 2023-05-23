package com.anton.server.commands;




import com.anton.common.exceptions.ExceptionInFileMode;
import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.exceptions.NoSuchElementException;
import com.anton.common.models.City;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Collection;
import java.util.Objects;

public class RemoveGreater extends AbstractCommand implements CollectionChanger{
    private CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager){
        super("remove_greater","удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager=collectionManager;


    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException, NoSuchElementException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        try{
            if (Objects.isNull(request.getObject())){
                return new Response(ResponseStatus.ASK_OBJECT,"Для команды "+this.getName()+" требуууется объект");

            }

            Collection<City> forRemove=collectionManager.getCollection()
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(city->city.compareTo(request.getObject())>0)
                    .toList();
            collectionManager.removeElements(forRemove);
            return new Response(ResponseStatus.OK,"Удалены элементы большие чем заданный");


        }catch (NoSuchElementException e){
            return new Response(ResponseStatus.ERROR,"Коллекция пустая");
        } catch (ExceptionInFileMode e){
            return new Response(ResponseStatus.ERROR,"Поля в файле не валидны, объект не создан");
        }
    }
}
