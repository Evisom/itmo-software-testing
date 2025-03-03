package org.example.task3;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Door> doors;
    private List<Person> peopleInside;
    private int noiseLevel;

    public Room() {
        this.doors = new ArrayList<>();
        this.peopleInside = new ArrayList<>();
        this.noiseLevel = 0;
    }

    public void addDoor(Door door) {
        doors.add(door);
    }

    public void addPerson(Person person) {
        peopleInside.add(person);
        noiseLevel += 10;
    }
}
