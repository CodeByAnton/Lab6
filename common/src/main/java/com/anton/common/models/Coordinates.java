package com.anton.common.models;

import java.io.Serializable;

public class Coordinates implements Validator, Serializable {
    private Integer x; //Значение поля должно быть больше -143, Поле не может быть null
    private Long y; //Значение поля должно быть больше -906
    public Coordinates(Integer x,Long y){
        this.x=x;
        this.y=y;
    }
    public Integer getX(){
        return x;
    }
    public void setX(Integer x){
        this.x=x;
    }
    public Long getY(){
        return y;
    }
    public void setY(Long y){
        this.y=y;
    }
    /**
     * check the fields
     * @return true if all fields valid
     */

    @Override
    public boolean validate() {



        if (this.x <= -143 || this.x == null) return false;
        return !(this.y == null || this.y <= -906);


    }
    @Override
    public String toString(){
        return "Coordinate x: "+this.x
                +" ;Coordinate y: "+this.y;
    }
}
