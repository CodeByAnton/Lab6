package com.anton.server.commands;



/**
 * Abstract class for all command
 */
public abstract class AbstractCommand implements Executable {
    private final String name;
    private final String description;
    public AbstractCommand(String name,String description){
        this.name=name;
        this.description=description;
    }
    public String getName(){
        return name;
    }
    public String getDescripion(){
        return description;
    }
    @Override
    public String toString(){
        return "name: "+this.name+"\n"+
                "description: "+this.description+"\n";
    }

}
