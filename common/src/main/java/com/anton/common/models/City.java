package com.anton.common.models;

import java.io.Serializable;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("City")
public class City implements Validator,Comparable<City>, Serializable {

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double area; //Значение поля должно быть больше 0
    private Integer population; //Значение поля должно быть больше 0, Поле не может быть null
    private Long metersAboveSeaLevel;
    private Climate climate; //Поле может быть null
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле не может быть null
    private Human governor; //Поле не может быть null
    private static int nextId=0;

    public City(String name, Coordinates coordinates, Date creationDate, double area, Integer population, Long metersAboveSeaLevel, Climate climate, Government government, StandardOfLiving standardOfLiving, Human governor) {
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }


    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public void setCoordinates(Coordinates coordinates){
        this.coordinates=coordinates;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate){
        this.creationDate=creationDate;
    }
    public double getArea(){
        return area;
    }
    public void setArea(double area){
        this.area=area;
    }
    public Integer getPopulation(){
        return population;
    }
    public void setPopulation(Integer population){
        this.population=population;
    }
    public Long getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }
    public void setMetersAboveSeaLevel(Long metersAboveSeaLevel){
        this.metersAboveSeaLevel=metersAboveSeaLevel;
    }
    public Human getGovernor(){
        return governor;
    }
    public void setGovernor(Human governor){
        this.governor=governor;
    }
    public Government getGovernment(){
        return government;
    }

    /**
     * Comparotor object by their id
     * @param o the object to be compared.
     * @return
     */

    @Override
    public int compareTo(City o){
        return this.population-o.population;
    }

    /**
     * check the fields
     * @return true if all fields valid
     */
    @Override
    public boolean validate() {
        if (this.id<=0) return false;
        if (this.name==null || this.name.isEmpty()) return false;
        if (this.coordinates==null) return false;
        if (this.area<=0) return false;
        return this.population > 0;
    }

    @Override
    public String toString(){
        return "City{"+'\n'+
                "id: "+id+'\n'+
                "name: "+name+'\n'+
                "coordinates: "+coordinates+'\n'+
                "creationDate: "+creationDate+'\n'+
                "area: "+area+'\n'+
                "population: "+population+'\n'+
                "metersAboveSeaLevel: "+metersAboveSeaLevel+'\n'+
                "climate: "+climate+'\n'+
                "government: "+government+'\n'+
                "standardOfLiving: "+standardOfLiving+'\n'+
                "governor: "+governor+'\n'+
                '}';


    }
}