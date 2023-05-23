package com.anton.common.models;

import java.io.Serializable;


public class Human implements  Serializable,Validator {
    private String name; //Поле не может быть null, Строка не может быть пустой
    public Human(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean validate() {
        return !(this.name==null || this.name.isEmpty());
    }
    @Override
    public String toString(){
        return "name: "+this.name;
    }
}

