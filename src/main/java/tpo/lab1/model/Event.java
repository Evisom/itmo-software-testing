package tpo.lab1.model;

import tpo.lab1.model.enums.EventType;

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

    public List<Person> getParticipants() {
        return participants;
    }

    public EventType getType() {
        return type;
    }
}
