package com.example.kafkatrackingvehicle.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
public class Vehicle {


    private int id;
    private int pointX;
    private int pointY;
    public Vehicle() {
    }

    public Vehicle( int id,
                   int pointX,
                   int pointY) {
        this.id = id;
        this.pointX = pointX;
        this.pointY = pointY;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return getId() == vehicle.getId() && getPointX() == vehicle.getPointX() && getPointY() == vehicle.getPointY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPointX(), getPointY());
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", pointX=" + pointX +
                ", pointY=" + pointY +
                '}';
    }
}
