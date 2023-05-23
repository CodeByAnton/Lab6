package com.anton.server.managers;



import com.anton.common.exceptions.InvalidFormException;
import com.anton.common.models.City;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.logging.Logger;

public class CollectionManager {
    private final HashSet<City> collection=new HashSet<>();
    private static int nextId=0;
    private LocalDateTime lastInitTime;
    static final Logger collectionMangerLogger=Logger.getLogger(CollectionManager.class.getName());

    public CollectionManager(){
        this.lastInitTime=LocalDateTime.now();
    }
    public HashSet<City> getCollection(){
        return collection;
    }
    public static void updateId(Collection<City> collection){
        nextId=collection.stream()
                .filter(Objects::nonNull)
                .map(City::getId)
                .max(Integer::compareTo)
                .orElse(0);
        collectionMangerLogger.info("Обновлено id на "+nextId);
    }
    public static int getNextId(){
        return ++nextId;
    }
    /**
     * Method to return String(yyyy-MM-dd HH:mm:ss)
     * @param localDateTime
     */
    public static String timeFormatter(LocalDateTime localDateTime){
        if (localDateTime == null) return null;

        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public String getLastInitTime(){
        return timeFormatter(lastInitTime);
    }

    public String collectionType(){
        return collection.getClass().getName();
    }
    public int collectionSize(){
        return collection.size();
    }
    public void clear(){
        this.collection.clear();
        lastInitTime=LocalDateTime.now();
        collectionMangerLogger.info("Коллекция очищена");
    }


    public City getById(int id){
        for (City element:collection){
            if(element.getId()==id) return element;
        }
        return null;
    }
    public void removeElement(City city){
        collection.remove(city);
    }
    public void addElement(City city) {
        collection.add(city);
        collectionMangerLogger.info("Объект добавлен в коллекцию");
    }
    public void editById(int id, City newElement) {
        City pastElement=this.getById(id);
        this.removeElement(pastElement);
        newElement.setId(id);
        this.addElement(newElement);
        collectionMangerLogger.info("Объект с id: "+id +" изменен");

    }
    public void addElements(Collection<City> collection) throws InvalidFormException {
        if(collection==null) return;
        for (City city:collection){
            this.addElement(city);
        }
    }
    public void removeElements(Collection<City> collection){
        this.collection.removeAll(collection);
    }
    public boolean checkExist(int id){
        return collection.stream().anyMatch((x)->x.getId()==id);
    }
}
