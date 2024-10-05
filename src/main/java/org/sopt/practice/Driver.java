package org.sopt.practice;

public class Driver {
    private Person person;

    Driver(Person person) {
        this.person = person;
    }

    boolean canDrive() {
        return person.getAge() > 20;
    }

}
