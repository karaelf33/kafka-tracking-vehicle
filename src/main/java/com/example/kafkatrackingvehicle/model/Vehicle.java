package com.example.kafkatrackingvehicle.model;

import java.io.Serializable;

public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int pointX;
    private int pointY;

    public Vehicle(int id, int pointX, int pointY) {
        this.id = id;
        this.pointX = pointX;
        this.pointY = pointY;
    }
}
