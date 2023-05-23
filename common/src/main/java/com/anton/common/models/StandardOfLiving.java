package com.anton.common.models;

import java.io.Serializable;

public enum StandardOfLiving implements Serializable {
    VERY_HIGH,
    ULTRA_LOW,
    NIGHTMARE;
    /**
     * @return all lines of enum
     */
    public static String names(){
        StringBuilder nameList=new StringBuilder();
        for (StandardOfLiving forms:values()){
            nameList.append(forms.name()).append("\n");
        }
        return nameList.substring(0,nameList.length()-1);

    }
}
