package com.anton.client.forms;


import com.anton.client.commandLine.*;
import com.anton.client.utils.ExecuteFileManager;
import com.anton.common.exceptions.ExceptionInFileMode;
import com.anton.common.models.Human;

/**
 * Form for human
 */
public class HumanForm extends AbstractForm<Human> {
    private final Printable console;
    private final UserInput scanner;

    public HumanForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Check name
     */
    private String askName(){
        while (true){
            console.println("Введите имя губернатора");
            String name=scanner.nextLine().trim();
            if (name.isEmpty() || name==null){
                console.printError("Имя не может быть путым или быть null");
                if ((Console.isFileMode())) throw new ExceptionInFileMode();
            }
            else{
                return name;
            }
        }
    }

    /**
     * Create new human element {@link Human}
     * @return object class {@link Human}
     */

    @Override
    public Human build() {
        return new Human(askName());
    }
}
