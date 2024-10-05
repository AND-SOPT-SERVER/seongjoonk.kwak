package org.sopt.practice;

public class Main {
    public static void main(String[] args) {
        Person person1 = new Person(30);
        Driver driver = new Driver(person1);

        Vehicle vehicle1 = new Car("GV80", "현대");
        Vehicle vehicle2 = new Cycle();

        System.out.println(vehicle1.run(driver));
        System.out.println(vehicle2.run(driver));

        if(driver.canDrive()) {
            System.out.println("합격");
        } else {
            System.out.println("불합격");
        }

    }
}
