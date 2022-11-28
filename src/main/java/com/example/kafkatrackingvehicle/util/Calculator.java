package com.example.kafkatrackingvehicle.util;

import com.example.kafkatrackingvehicle.model.Vehicle;

public class Calculator {
    private Calculator() {
    }

    public static Double calculateDistance(Vehicle vehicle){

        return Math.sqrt(vehicle.getPointY()*vehicle.getPointY() +vehicle.getPointX()*vehicle.getPointX());
    }
}
