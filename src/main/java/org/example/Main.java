package org.example;

import org.example.task3.Person;
import org.example.task3.Room;
import org.example.task3.enams.Emotion;
import org.example.task3.enams.Event;
import org.example.task3.enams.EventType;
import org.example.task3.University;

public class Main {
    public static void main(String[] args) {
        University university = new University("Kruxwan University", "Blue Belt");
        Person intruder1 = new Person("Intruder 1", "Scholar", Emotion.ANGRY, university);
        Person intruder2 = new Person("Intruder 2", "Scholar", Emotion.ANGRY, university);
        Room room = new Room();

        intruder1.enterRoom(room);
        intruder2.enterRoom(room);

        Event invasion = new Event(EventType.INVASION, room, "Breaking into the room");
        invasion.addParticipant(intruder1);
        invasion.addParticipant(intruder2);

        System.out.println("Event created: " + invasion);
    }
}