package com.anton.server.commands;


import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;
import com.anton.server.managers.CollectionManager;

/**
 * Command that print information about collection
 */
public class Info extends AbstractCommand{
    private CollectionManager collectionManager;


    public Info( CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        this.collectionManager = collectionManager;

    }


    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        if (!request.getArgs().isBlank()) throw new IllegalArgumentException();
        String lastInitTime = (collectionManager.getLastInitTime() == null || collectionManager.getCollection().size()==0)
                ? "В сессии коллекция не инициализирована"
                : collectionManager.getLastInitTime();
        String string = "Информация о коллекции: \n"+
                "Тип: "+collectionManager.collectionType()+"\n"+
                "Количество элементов: "+ collectionManager.collectionSize()+"\n"+
                "Дата последней инициализации: "+lastInitTime+ "\n";
        ;
        return new Response(ResponseStatus.OK,string);
    }
}
