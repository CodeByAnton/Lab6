package com.anton.server;

import com.anton.common.exceptions.ExitException;
import com.anton.server.commands.*;
import com.anton.server.managers.CollectionManager;
import com.anton.server.managers.CommandManager;
import com.anton.server.managers.FileManager;
import com.anton.server.utils.BlankConsole;
import com.anton.server.utils.Printable;
import com.anton.server.utils.RequestHandler;
import com.anton.server.utils.Server;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class ServerApp extends Thread{
    public static int PORT=25052;
    public static final int CONNECTION_TIMEOUT=60*1000;
    private static final Printable console=new BlankConsole();
    static final Logger rootLogger=Logger.getLogger(CollectionManager.class.getName());
    public static void main(String args[]){
        if (args.length== 2){
            try{
                PORT=Integer.parseInt(args[1]);

            } catch (NumberFormatException ignored){
        }


        }

        CollectionManager collectionManager=new CollectionManager();
        try {
            FileManager fileManager = new FileManager(console, collectionManager, args[0]);
            try{
                ServerApp.rootLogger.info("Создание объектов");
                fileManager.findFile();
                fileManager.createObjects();
                ServerApp.rootLogger.info("Создание объектов успешно завершено");

            } catch (ExitException e){
                console.println("До встречи");

                ServerApp.rootLogger.warning("Ошибка во время создания объектов");
                return;

            }
            CommandManager commandManager=new CommandManager(fileManager);
            commandManager.addCommand(List.of(
                    new Help(commandManager),
                    new Info(collectionManager),
                    new Show(collectionManager),
                    new AddElement(collectionManager),
                    new Update(collectionManager),
                    new Clear(collectionManager),
                    new ExecuteScript(),
                    new Exit(),
                    new FilterByGovernment(collectionManager),
                    new History(commandManager),
                    new MinByPopulation(collectionManager),
                    new PrintAscending(collectionManager),
                    new RemoveById(collectionManager),
                    new RemoveGreater(collectionManager),
                    new RemoveLower(collectionManager)


            ));
            ServerApp.rootLogger.fine("Создан объект менеджера команд");
            RequestHandler requestHandler=new RequestHandler(commandManager);
            ServerApp.rootLogger.fine("Создан объект обработчика запросов");
            Server server=new Server(PORT,CONNECTION_TIMEOUT,requestHandler,fileManager);
            ServerApp.rootLogger.fine("Создан объект сервера");
            server.run();


            }
        catch (ArrayIndexOutOfBoundsException e){
            console.printError("Файл должен передавться в качестве аргумента командной строки");
            ServerApp.rootLogger.warning("Файл должен передаваться в качестве аргумента строки ");

        }

        }
}
