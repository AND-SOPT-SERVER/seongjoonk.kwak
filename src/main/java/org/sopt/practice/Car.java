package org.sopt.practice;

public class Car implements Vehicle {

    private final String name;
    private final String manufacturer;
    private int fuel;

    public Car(String name, String manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public void bbang() {
        System.out.println("빵!");
    }

    public String run(Driver driver) {
        if(driver.canDrive()) {
            return "자동차 시동";
        } else {
            return "자동차 시동 못걺";
        }
    }

    public String getName() {
        return this.name;
    }

//    public void setName(String newName) {
//        this.name = newName;
//    }
}

