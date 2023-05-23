package com.anton.client.utils;



import com.anton.client.commandLine.Console;
import com.anton.client.commandLine.Printable;
import com.anton.client.forms.CityForm;
import com.anton.common.exceptions.ExceptionInFileMode;
import com.anton.common.exceptions.ExitException;
import com.anton.common.exceptions.InvalidFormException;
import com.anton.common.exceptions.NoSuchElementException;
import com.anton.common.models.City;
import com.anton.common.network.Request;
import com.anton.common.network.Response;
import com.anton.common.network.ResponseStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class for work with user input
 */
public class RuntimeManager {
    private final Printable console;
    private final Scanner userScanner;
    private final Client client;
    public RuntimeManager(Printable console,Scanner userScanner,Client client){
        this.console=console;
        this.userScanner=userScanner;
        this.client=client;
    }
    private void printResponse(Response response){
        switch (response.getStatus()){
            case OK -> {
                if ((Objects.isNull(response.getCollection()))){
                    console.println(response.getResponse());
                }
                else {
                    console.println(response.getResponse()+"\n"+response.getCollection().toString());
                }
            }
            case ERROR -> console.printError(response.getResponse());
            case WRONG_ARGUMENTS -> console.printError("Неверное использование команды");
            default -> {}
        }

    }
    public void interactiveMode(){
        while (true){
            try {
                if (!userScanner.hasNext()) throw new ExitException();
                String[] userCommand=(userScanner.nextLine().trim()+" ").split(" ",2);
                Response response=client.sendAndAskResponse(new Request(userCommand[0].trim(),userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()){
                    case ASK_OBJECT -> {
                        City city=new CityForm(console).build();
                        /*if (!city.validate()) {
                            console.printError("Truwekqbfukwqbgfdiulsvfgcyuilwevyvfewjyv");
                            System.out.println(city.validate());
                            throw new InvalidFormException();

                        };*/
                        Response newResponse=client.sendAndAskResponse(
                                new Request(userCommand[0].trim(),userCommand[1].trim(),city)
                        );
                        if (newResponse.getStatus()!= ResponseStatus.OK){
                            console.printError(newResponse.getResponse());
                        }
                        else {
                            this.printResponse(newResponse);
                        }

                    }
                    case EXIT -> throw new ExitException();
                    case EXECUTE_SCRIPT -> {
                        Console.setFileMode(true);
                        this.fileExecution(response.getResponse());
                        Console.setFileMode(false);
                    }
                    default -> {}
                }

            } /*catch (InvalidFormException e){
                console.printError("Поля не валидны, объект не создан");
            }*/ catch (NoSuchElementException e){
                console.printError("Пользовательский ввод не обнаружен");
            } catch (ExitException e){
                console.println("До встречи");
                return;
            }
        }
    }
    private void fileExecution(String args) throws ExitException{
        if (args == null || args.isEmpty()) {
            console.printError("Путь не распознан");
            return;
        }
        else console.println("Путь получен успешно");
        args = args.trim();
        try {
            ExecuteFileManager.pushFile(args);
            for (String line = ExecuteFileManager.readLine(); line != null; line = ExecuteFileManager.readLine()) {
                String[] userCommand = (line + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                if (userCommand[0].isBlank()) return;
                if (userCommand[0].equals("execute_script")){
                    if(ExecuteFileManager.fileRepeat(userCommand[1])){
                        console.printError("Найдена рекурсия по пути " + new File(userCommand[1]).getAbsolutePath());
                        continue;
                    }
                }
                console.println("Выполнение команды " + userCommand[0]);
                Response response = client.sendAndAskResponse(new Request(userCommand[0].trim(), userCommand[1].trim()));
                this.printResponse(response);
                switch (response.getStatus()){
                    case ASK_OBJECT -> {
                        City city;
                        try{
                            city = new CityForm(console).build();
                            /*if (city.validate()) {
                                System.out.println(city.validate());
                                System.out.println(city.getCoordinates().validate());
                                System.out.println("sdovjiuiwegviuweg");
                                throw new ExceptionInFileMode();
                            }*/
                        } catch (ExceptionInFileMode | NullPointerException err){
                            console.printError("Поля в файле не валидны! Объект не создан");

                            continue;
                        }
                        Response newResponse = client.sendAndAskResponse(
                                new Request(
                                        userCommand[0].trim(),
                                        userCommand[1].trim(),
                                        city));
                        if (newResponse.getStatus() != ResponseStatus.OK){
                            console.printError(newResponse.getResponse());
                        }
                        else {
                            this.printResponse(newResponse);
                        }
                    }
                    case EXIT -> throw new ExitException();
                    case EXECUTE_SCRIPT -> {
                        this.fileExecution(response.getResponse());
                        ExecuteFileManager.popRecursion();
                    }
                    default -> {}
                }
            }
            ExecuteFileManager.popFile();
        } catch (FileNotFoundException fileNotFoundException){
            console.printError("Такого файла не существует");
        } catch (IOException e) {
            console.printError("Ошибка ввода вывода");
        }
    }



}
