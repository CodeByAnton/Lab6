package com.anton.server.managers;

import com.anton.common.exceptions.ExitException;
import com.anton.common.exceptions.InvalidFormException;
import com.anton.common.models.City;
import com.anton.server.utils.Console;
import com.anton.server.utils.Printable;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.security.AnyTypePermission;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileManager {
    private String text;
    private final Printable console;
    private final CollectionManager collectionManager;
    private final String filePath;
    static final Logger fileManagerLogger=Logger.getLogger(CollectionManager.class.getName());



    private final XStream xStream=new XStream();

    /**
     * Create alias for XStream
     * @param console User input/output
     * @param collectionManager Work with collection
     * @param filePath Path to xml file
     */
    public FileManager(Printable console, CollectionManager collectionManager, String filePath){
        this.console=console;
        this.filePath=filePath;
        this.collectionManager=collectionManager;
        this.xStream.alias("City", City.class);
        this.xStream.alias("HashSet",CollectionManager.class);
        this.xStream.addPermission(AnyTypePermission.ANY);
        this.xStream.addImplicitCollection(CollectionManager.class,"collection");
        fileManagerLogger.info("Созданы алиасы для xstream");

    }
    public String getFilePath(){
        return filePath;
    }



    /**
     * Read collection from file
     */

    public void findFile() throws ExitException {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            scanner.close();
            if (stringBuilder.isEmpty()) {
                console.printError("Файл пустой");
                this.text = "</HashSet>";
                return;
            }
            this.text = stringBuilder.toString();
        } catch (FileNotFoundException e) {

            console.printError("Такого файла не существует или нужно изменить права доступа");
            throw new ExitException();
        } /*catch (IOException ioe) {
            console.printError("Ошибка ввода/вывода" + ioe);
            throw new ExitException();
        }*/
    }


    /**
     * Create objects
     * @throws ExitException when objects not valid
     */
    public void createObjects() throws ExitException, NullPointerException {
        try{
            XStream xstream=new XStream();
            xstream.alias("City",City.class);
            xstream.alias("HashSet",CollectionManager.class);
            xstream.addPermission(AnyTypePermission.ANY);
            xstream.addImplicitCollection(CollectionManager.class,"collection");
            fileManagerLogger.info("Xstream сконфигурован");
            CollectionManager collectionManagerWithObjects=(CollectionManager) xstream.fromXML(this.text);
            fileManagerLogger.info("Файл прочитан");
            for (City city:collectionManagerWithObjects.getCollection()){
                if (this.collectionManager.checkExist(city.getId())){
                    console.printError("В файле повторяются id");
                    fileManagerLogger.severe("В файле повторяются id");
                    throw new ExitException();
                }
                if (!city.validate() ) throw new InvalidFormException();
                this.collectionManager.addElement(city);
                fileManagerLogger.info("Добавлен новый объект");


            }
            this.collectionManager.addElements(collectionManagerWithObjects.getCollection());

        }catch (InvalidFormException | StreamException | ConversionException | NullPointerException e){

            if (new File(filePath).length()!=0) {
                fileManagerLogger.warning("Объекты в файле не валидны");
                console.printError("Объекты в файле не валидны");
            }

        }
        CollectionManager.updateId(collectionManager.getCollection());
    }

    /**
     * Save collection in file
     */
    public void saveObjects()  {


        try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8);
                this.xStream.toXML(collectionManager, writer);
                writer.close();
                fileManagerLogger.info("Коллекция записана в файл");
            } catch (FileNotFoundException e) {
                console.printError("Поправьте права доступа");
            } catch (IOException e) {
                console.printError("Ошибка ввода вывода");
            }
        }
    }

