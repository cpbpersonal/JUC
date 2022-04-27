package com.single;

public class Hungary {

    private Hungary(){
    }
    private final static Hungary HUNGARY=new Hungary();
    private static Hungary getInstance(){
        return HUNGARY;
    }
}
