package org.example.task3;

import org.example.task3.enams.Emotion;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private String role;
    private Emotion emotion;
    private List<Clothing> clothing;
    private Organization affiliation;

    public Person(String name, String role, Emotion emotion, Organization affiliation) {
        this.name = name;
        this.role = role;
        this.emotion = emotion;
        this.affiliation = affiliation;
        this.clothing = new ArrayList<>();
    }

    public void addClothing(Clothing item) {
        clothing.add(item);
    }

    public void enterRoom(Room room) {
        room.addPerson(this);
    }
}
