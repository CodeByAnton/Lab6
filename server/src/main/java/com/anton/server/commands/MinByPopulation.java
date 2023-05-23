package com.anton.server.commands;




import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.models.City;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/**
 * Command that print element from collection{@link City} which value of field population is minimal
 */
public class MinByPopulation extends AbstractCommand{
    private CollectionManager collectionManager;

    public MinByPopulation(CollectionManager collectionManager){
        super("min_by_population","вывести любой объект из коллекции, значение поля population которого является минимальным ");
        this.collectionManager=collectionManager;

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        Collection<City> city=collectionManager.getCollection();
        Optional<City> k = city.stream()
                .min(Comparator.comparing(City::getPopulation));

        if (k.isPresent()) {
             return new Response(ResponseStatus.OK,"Объект с минмальным значением population: "+k);
        }
        else {
            return new Response(ResponseStatus.ERROR,"Коллекция ещё не инициализирована");
        }
    }
}
