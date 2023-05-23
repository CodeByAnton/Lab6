package com.anton.common.models;

import java.io.Serializable;

public enum Government implements Serializable {
    KRITARCHY,
    MERITOCRACY,
    MONARCHY;
    /**
     * @return all lines of enum
     */
    public static String names(){
        StringBuilder nameList=new StringBuilder();
        for (Government forms:values()){
            nameList.append(forms.name()).append("\n");
        }
        return nameList.substring(0,nameList.length()-1);

    }
}