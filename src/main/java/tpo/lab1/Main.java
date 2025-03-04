package tpo.lab1;

import tpo.lab1.model.Person;
import tpo.lab1.model.Room;
import tpo.lab1.model.enums.Emotion;
import tpo.lab1.model.Event;
import tpo.lab1.model.enums.EventType;
import tpo.lab1.model.University;

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