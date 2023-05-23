package com.anton.server.commands;


import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.models.City;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;


public class PrintAscending extends AbstractCommand{
    private CollectionManager collectionManager;

    public PrintAscending(CollectionManager collectionManager){
        super("print_ascending","вывести элементы коллекции в порядке возрастания");
        this.collectionManager=collectionManager;

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentsException();
        Collection<City> cities=collectionManager.getCollection();
        Collection<City> ascendingCity=new ArrayList<>();
        cities.stream()
                .sorted(Comparator.comparing(City::getArea) )
                .forEach(city ->ascendingCity.add(city));
        return new Response(ResponseStatus.OK,"Коллекция в порядке возрастания: ",ascendingCity);

    }
}
