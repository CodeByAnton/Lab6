package com.anton.server.commands;




import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.models.City;
import com.anton.common.models.Government;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command that return all collection{@link City} element, which field equals given
 */
public class FilterByGovernment extends AbstractCommand{
    private CollectionManager collectionManager;

    public FilterByGovernment(CollectionManager collectionManager){
        super("filter_by_government","вывести элементы, значение поля government которых равно заданному");
        this.collectionManager=collectionManager;

    }

    @Override
    public Response execute(Request request) throws IllegalArgumentsException {
        if (request.getArgs().isBlank()) {
            throw new IllegalArgumentsException();
        }
        try {
            Collection<City> collection = collectionManager.getCollection();
            Government government = Government.valueOf(request.getArgs().trim().toUpperCase());

            List<City> filteredCities = collection.stream()
                    .filter(city -> city.getGovernment() == government)
                    .collect(Collectors.toList());

            if (filteredCities.isEmpty()) {
                return new Response(ResponseStatus.ERROR, "Нет элементов с данным видом правительства: " + government);
            } else {
                return new Response(ResponseStatus.OK, "Коллекция с данным видом правительства: ", filteredCities);
            }
        } catch (IllegalArgumentException e) {
            return new Response(ResponseStatus.ERROR, "Поле government должно иметь одно из следующих значений: " + "\n"
                    + "KRITARCHY" + "\n"
                    + "MERITOCRACY" + "\n"
                    + "MONARCHY");
        }
    }

}
