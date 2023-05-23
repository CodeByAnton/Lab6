package com.anton.common.network;




import com.anton.common.models.City;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

public class Response implements Serializable {
    private final ResponseStatus status;
    private String response="";
    private Collection<City> collection;
    public Response(ResponseStatus status){
        this.status=status;
    }
    public Response (ResponseStatus status,String response){
        this.status=status;
        this.response=response;
    }
    public Response(ResponseStatus status,String response,Collection<City> collection){
        this.status=status;
        this.response=response.trim();
        this.collection=collection.stream().sorted(Comparator.comparing(City::getId)).toList();
    }
    public ResponseStatus getStatus(){
        return status;
    }
    public String getResponse(){
        return response;
    }
    public Collection<City> getCollection(){
        return collection;
    }
}
