package com.anton.server.managers;



import com.anton.common.exceptions.CommandRuntimeException;
import com.anton.common.exceptions.ExitException;
import com.anton.common.exceptions.IllegalArgumentsException;
import com.anton.common.exceptions.NoSuchCommandException;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.server.commands.AbstractCommand;
import com.anton.server.commands.CollectionChanger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommandManager {
    private final HashMap<String, AbstractCommand> commands=new HashMap<>();
    private final ArrayList<String> commandHistory=new ArrayList<>();
    private final FileManager fileManager;

    static final Logger commandManagerLogger=Logger.getLogger(CommandManager.class.getName());
    public CommandManager(FileManager fileManager){

        this.fileManager=fileManager;

    }
    public void addCommand(AbstractCommand command){
        this.commands.put(command.getName(),command);
        commandManagerLogger.info("Добавлена команда "+command.getName());
    }
    public void addCommand(Collection<AbstractCommand> commands){
        this.commands.putAll(commands.stream()
                .collect(Collectors.toMap(AbstractCommand::getName,s->s)));
        commandManagerLogger.info("Добавлены комманды "+commands);

    }
    public Collection<AbstractCommand> getCommands(){
        return commands.values();
    }
    public void addToHistory(String string){
        if (string.isBlank()) return;
        this.commandHistory.add(string);
        commandManagerLogger.info("Добавлена команда в историю: "+string);
    }
    public List<String> getCommandHistory(){
        return commandHistory;
    }
    public Response execute(Request request) throws NoSuchCommandException, IllegalArgumentsException, CommandRuntimeException, ExitException {
        AbstractCommand command=commands.get(request.getCommandName());
        if (command==null){
            commandManagerLogger.severe("Такой команды нет");
            throw new NoSuchCommandException();
        }
        Response response=command.execute(request);
        commandManagerLogger.info("Выполнение команды");
        if (command instanceof CollectionChanger){
            commandManagerLogger.info("Коллекция изменена");
            fileManager.saveObjects();
        }
        return response;
    }

}
