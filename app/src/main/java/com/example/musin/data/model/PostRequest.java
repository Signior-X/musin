package com.example.musin.data.model;

public class PostRequest {
    private String name;

    public PostRequest(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
