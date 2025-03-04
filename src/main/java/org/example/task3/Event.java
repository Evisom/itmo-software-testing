package org.example.task3.enams;

import org.example.task3.Person;
import org.example.task3.Room;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private EventType type;
    private List<Person> participants;
    private Room location;
    private String cause;

    public Event(EventType type, Room location, String cause) {
        this.type = type;
        this.location = location;
        this.cause = cause;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Person person) {
        participants.add(person);
    }
}
