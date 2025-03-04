package org.example;

import org.example.task3.University;
import org.example.task3.enams.ClothingType;
import org.example.task3.enams.Emotion;
import org.example.task3.enams.EventType;
import org.example.task3.enams.LockStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DomainModelTest {

    private University university;
    private Room room;
    private Person person;
    private Door door;
    private Event event;

    @BeforeEach
    void setUp() {
        university = new University("Круксванский Университет", "Синий символ");
        room = new Room();
        person = new Person("Альтаир", "Маг", Emotion.ANGRY, university);
        door = new Door(false, LockStatus.LOCKED);
        event = new Event(EventType.INVASION, room, "Маги ворвались в зал");
    }

    // 🔹 1️⃣ Тест на создание университета
    @Test
    @DisplayName("Создание университета")
    void testUniversityCreation() {
        assertEquals("Круксванский Университет", university.getName());
        assertEquals("Синий символ", university.getSymbol());
    }

    // 🔹 2️⃣ Тест на добавление дверей в комнату
    @Test
    @DisplayName("Добавление двери в комнату")
    void testAddDoorToRoom() {
        room.addDoor(door);
        assertFalse(door.isOpen());  // Дверь изначально закрыта
    }

    // 🔹 3️⃣ Тест на вход персонажа в комнату
    @Test
    @DisplayName("Вход персонажа в комнату")
    void testPersonEnteringRoom() {
        room.addPerson(person);
        assertEquals(1, room.getPeopleInside().size());
    }

    // 🔹 4️⃣ Проверка эмоций персонажа
    @ParameterizedTest
    @CsvSource({"ANGRY", "CALM", "EXCITED", "SCARED"})
    @DisplayName("Проверка установки эмоций")
    void testPersonEmotions(Emotion emotion) {
        Person testPerson = new Person("Магистр", "Преподаватель", emotion, university);
        assertEquals(emotion, testPerson.getEmotion());
    }

    // 🔹 5️⃣ Проверка добавления одежды персонажу
    @Test
    @DisplayName("Добавление одежды персонажу")
    void testPersonClothing() {
        Clothing robe = new Clothing(ClothingType.ROBE, "Синий");
        person.addClothing(robe);
        assertEquals(1, person.getClothing().size());
        assertEquals(ClothingType.ROBE, person.getClothing().get(0).getType());
    }

    // 🔹 6️⃣ Проверка открытия и закрытия двери
    @Test
    @DisplayName("Открытие и закрытие двери")
    void testDoorOpenClose() {
        door.open();
        assertTrue(door.isOpen());

        door.close();
        assertFalse(door.isOpen());
    }

    // 🔹 7️⃣ Проверка изменения состояния замка двери
    @ParameterizedTest
    @CsvSource({"LOCKED", "UNLOCKED"})
    @DisplayName("Проверка состояния замка двери")
    void testDoorLockStatus(LockStatus lockStatus) {
        Door testDoor = new Door(false, lockStatus);
        assertEquals(lockStatus, testDoor.getLockStatus());
    }

    // 🔹 8️⃣ Создание события и добавление участников
    @Test
    @DisplayName("Добавление участников в событие")
    void testEventParticipants() {
        event.addParticipant(person);
        assertEquals(1, event.getParticipants().size());
        assertEquals("Альтаир", event.getParticipants().get(0).getName());
    }

    // 🔹 9️⃣ Проверка типов событий
    @ParameterizedTest
    @CsvSource({"INVASION", "CONVERSATION", "FIGHT"})
    @DisplayName("Проверка типов событий")
    void testEventTypes(EventType eventType) {
        Event testEvent = new Event(eventType, room, "Тестовое событие");
        assertEquals(eventType, testEvent.getType());
    }

    // 🔹 🔟 Проверка уровня шума в комнате при добавлении персонажей
    @Test
    @DisplayName("Проверка увеличения шума в комнате")
    void testRoomNoiseLevel() {
        Person p1 = new Person("Маг1", "Студент", Emotion.EXCITED, university);
        Person p2 = new Person("Маг2", "Преподаватель", Emotion.CALM, university);

        room.addPerson(p1);
        room.addPerson(p2);

        assertEquals(20, room.getNoiseLevel());  // +10 за каждого персонажа
    }
}
