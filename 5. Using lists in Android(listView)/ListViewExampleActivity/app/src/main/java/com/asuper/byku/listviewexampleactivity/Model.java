package com.asuper.byku.listviewexampleactivity;

/**
 * Created by Byku on 18.05.2016.
 */
//this will hold the name and information if this element is selected
public class Model {
    private String name;
    private boolean selected;

    public Model(String name){
        this.name = name;
        selected = false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
}
