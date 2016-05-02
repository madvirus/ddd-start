package com.myshop.eventstore.infra;

public class SampleEvent {

    private String name;
    private int value;

    public SampleEvent() {
    }

    public SampleEvent(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
