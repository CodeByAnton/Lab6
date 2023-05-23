package com.anton.client.forms;


import com.anton.client.commandLine.*;
import com.anton.client.utils.ExecuteFileManager;
import com.anton.common.exceptions.ExceptionInFileMode;
import com.anton.common.models.Coordinates;

/**
 * Form for coordinates
 */

public class CoordinatesForm extends AbstractForm<Coordinates> {
    private final Printable console;
    private final UserInput scanner;

    public CoordinatesForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Check value X
     */
    private Integer askX(){
        while (true){
            console.println("Введите координату X");
            String input=scanner.nextLine().trim();
            try {
                if (Integer.parseInt(input)>-143){
                    return Integer.parseInt(input);
                }
                else {
                    console.printError("X должно быть больше -143");
                }
            }
            catch (NumberFormatException e){
                console.printError("X должно быть числом типа Integer");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }

    /**
     * Check value Y
     */
    private Long askY(){
        while (true){
            console.println("Введите координату Y");
            String input=scanner.nextLine().trim();
            try {
                if (Integer.parseInt(input)>-906){
                    return Long.parseLong(input);
                }
                else {
                    console.printError("Y должно быть больше -906");
                }
            }
            catch (NumberFormatException e){
                console.printError("Y должно быть числом типа Long");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }

    /**
     * Create new element {@link Coordinates}
     * @return object class {@link Coordinates}
     */
    @Override
    public Coordinates build() {
        return new Coordinates(askX(),askY());
    }
}
