package com.example.oa;

/**
 * Created by ${user} on 19-4-30
 */
public class Messages {
    public String name;
    public String title;

    public Messages(){

    }

    public Messages(String name, String title, String date) {
        this.name = name;
        this.title = title;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;



}
