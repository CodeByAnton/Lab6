package com.anton.client.forms;






import com.anton.client.commandLine.*;
import com.anton.client.utils.ExecuteFileManager;
import com.anton.common.exceptions.ExceptionInFileMode;
import com.anton.common.models.*;

import java.util.Date;

/**
 * Form for city
 * @author anton
 */

public class CityForm extends AbstractForm<City> {
    private final Printable console;
    private final UserInput scanner;
    public CityForm(Printable console) {
        this.console = (Console.isFileMode())
                ? new BlankConsole()
                : console;
        this.scanner = (Console.isFileMode())
                ? new ExecuteFileManager()
                : new ConsoleInput();
    }

    /**
     * Check value x
     * @return name if name is valid
     */
    private String askName(){
        while(true){
            console.println("Введите имя города ");
            String name=scanner.nextLine().trim();
            if (name.isEmpty() || name==null){
                console.printError("Имя не может быть пустым или null");
                if (Console.isFileMode()) throw new ExceptionInFileMode();

            }
            else {
                return name;
            }
        }
    }
    private Coordinates askCoordinates(){
        return new CoordinatesForm(console).build();
    }
    private double askArea(){
        while (true){
            console.println("Введите площадь");
            String input=scanner.nextLine().trim();
            try{
                if (Double.parseDouble(input)>0){
                    return Double.parseDouble(input);
                }
                else {
                    console.printError("Площадь должна быть больше 0");
                }
            } catch (NumberFormatException e){
                console.printError("Площадь должна быть типа double");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }
    }
    private Integer askPopulation(){
        while (true){
            console.println("Введите количесвто населения ");
            String input=scanner.nextLine().trim();
            try{
                if (Integer.parseInt(input)>0){
                    return Integer.parseInt(input);
                }
                else {
                    console.printError("Значение населения должно быть больше 0");
                }
            } catch (NumberFormatException e){
                console.printError("Население должно быть типом Integer");
                if (Console.isFileMode()) throw new ExceptionInFileMode();
            }
        }

    }
    private Long askMeterAboveSeaLevel(){
        while (true){
            console.println("Введите высоту над уровнем моря");

            String input=scanner.nextLine().trim();
            try{
                return Long.parseLong(input);
            } catch (NumberFormatException e){
                console.printError("Высота над уровнем должна быть числом типа Long");
            }
        }
    }
    private Climate askClimate(){
        return new ClimateForm(console).build();
    }
    private Government askGovernment(){
        return new GovernmentForm(console).build();
    }
    private StandardOfLiving askStandardOfLiving(){
        return new StandardOfLivingForm(console).build();
    }
    private Human askGovernor(){
        return new HumanForm(console).build();
    }

    /**
     * Create City object
     * @return City object
     */

    @Override
    public City build() {
        return new City(
        askName(),
        askCoordinates(),
        new Date(),
        askArea(),
        askPopulation(),
        askMeterAboveSeaLevel(),
        askClimate(),
        askGovernment(),
        askStandardOfLiving(),
        askGovernor()
        );
    }
}
