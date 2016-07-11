package com.asuper.byku.sqlitedatabase;

/**
 * Created by Byku on 19.06.2016.
 */
//It is our model, contains the data we will save in the database and show in the user interface
public class Comment {
    static int nrOfComments;
    static{
        System.out.printf("Initializing comments");
        nrOfComments = 0;
    }
    private long id;
    private String comment;

    Comment(){}

    Comment(long id, String comment){
        this.id = id;
        this.comment = comment;
    }


    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    //Will bbe used by the ArrayDapter in the ListView
    @Override
    public String toString(){
        return comment;
    }
}