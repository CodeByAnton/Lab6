package com.anton.common.network;



import com.anton.common.models.City;

import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String args="";
    private City object=null;
    public Request(ResponseStatus ok,String commandName, City help){
        this.commandName=commandName.trim();
    }
    public Request(String commandName,String args){
        this.commandName=commandName.trim();
        this.args=args;
    }
    public Request(String commandName, City object){
        this.commandName=commandName.trim();
        this.object=object;
    }
    public Request(String commandName,String args,City object){
        this.commandName=commandName;
        this.args=args;
        this.object=object;
    }
    public boolean isEmpty(){
        return commandName.isEmpty() && args.isEmpty() && object==null;
    }
    public String getCommandName(){
        return commandName;
    }
    public String getArgs(){
        return args;
    }
    public City getObject(){
        return object;
    }

}
