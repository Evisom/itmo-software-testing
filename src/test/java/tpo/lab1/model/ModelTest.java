package tpo.lab1.model;

import tpo.lab1.model.enums.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private University university;
    private Room room;
    private Person person;
    private Door door;
    private Event event;
    @BeforeAll
    static void info() {
        System.out.println("Запускаем тестики на доменную модель...");
    }
    @BeforeEach
    void setUp() {
        university = new University("Круксванский Университет", "Синий символ");
        room = new Room();
        person = new Person("Альтаир", "Маг", Emotion.ANGRY, university);
        door = new Door(false, LockStatus.LOCKED);
        event = new Event(EventType.INVASION, room, "Маги ворвались в зал", new BlockAttempt());
    }
    @Test
    @DisplayName("Создание университета")
    void testUniversityCreation() {
        assertEquals("Круксванский Университет", university.getName());
        assertEquals("Синий символ", university.getSymbol());
    }
    @Test
    @DisplayName("Добавление двери в комнату")
    void testAddDoorToRoom() {
        room.addDoor(door);
        assertFalse(door.isOpen());
    }
    @Test
    @DisplayName("Вход персонажа в комнату")
    void testPersonEnteringRoom() {
        room.addPerson(person);
        assertEquals(1, room.getPeopleInside().size());
    }
    @ParameterizedTest
    @CsvSource({"ANGRY", "CALM", "EXCITED", "SCARED"})
    @DisplayName("Проверка установки эмоций")
    void testPersonEmotions(Emotion emotion) {
        Person testPerson = new Person("Магистр", "Преподаватель", emotion, university);
        assertEquals(emotion, testPerson.getEmotion());
    }
    @Test
    @DisplayName("Добавление одежды персонажу")
    void testPersonClothing() {
        Clothing robe = new Clothing(ClothingType.ROBE, "Синий");
        person.addClothing(robe);
        assertEquals(1, person.getClothing().size());
        assertEquals(ClothingType.ROBE, person.getClothing().get(0).getType());
    }
    @Test
    @DisplayName("Открытие и закрытие двери")
    void testDoorOpenClose() {
        door.open();
        assertTrue(door.isOpen());

        door.close();
        assertFalse(door.isOpen());
    }
    @ParameterizedTest
    @CsvSource({"LOCKED", "UNLOCKED"})
    @DisplayName("Проверка состояния замка двери")
    void testDoorLockStatus(LockStatus lockStatus) {
        Door testDoor = new Door(false, lockStatus);
        assertEquals(lockStatus, testDoor.getLockStatus());
    }
    @Test
    @DisplayName("Добавление участников в событие")
    void testEventParticipants() {
        event.addParticipant(person);
        assertEquals(1, event.getParticipants().size());
        assertEquals("Альтаир", event.getParticipants().get(0).getName());
    }
    @ParameterizedTest
    @CsvSource({"INVASION", "CONVERSATION", "FIGHT"})
    @DisplayName("Проверка типов событий")
    void testEventTypes(EventType eventType) {
        Event testEvent = new Event(eventType, room, "Тестовое событие",new BlockAttempt());
        assertEquals(eventType, testEvent.getType());
    }
    @Test
    @DisplayName("Проверка увеличения шума в комнате")
    void testRoomNoiseLevel() {
        Person p1 = new Person("Маг1", "Студент", Emotion.EXCITED, university);
        Person p2 = new Person("Маг2", "Преподаватель", Emotion.CALM, university);

        room.addPerson(p1);
        assertEquals(10, room.getNoiseLevel());
        room.addPerson(p2);
        assertEquals(20, room.getNoiseLevel());
    }
    @Test
    @DisplayName("Проверка цвета одежды")
    void testClothingColor() {
        Clothing belt = new Clothing(ClothingType.BELT, "Красный");
        assertEquals("Красный", belt.getColor());
    }
    @Test
    @DisplayName("Проверка пустой комнаты")
    void testEmptyRoom() {
        assertTrue(room.getPeopleInside().isEmpty());
    }
    @Test
    @DisplayName("Проверка пустого события")
    void testEmptyEvent() {
        assertTrue(event.getParticipants().isEmpty());
    }



    @Test
    public void testBlockAttemptFailed() {
        University university = new University("Kruxwan University", "Blue Belt");
        Person intruder1 = new Person("Intruder 1", "Scholar", Emotion.ANGRY, university);
        Person intruder2 = new Person("Intruder 2", "Scholar", Emotion.ANGRY, university);
        Person butler = new Person("Butler", "Servant", Emotion.SCARED, null);

        BlockAttempt blockAttempt = new BlockAttempt(
                List.of(butler),
                Arrays.asList(intruder1, intruder2),
                BlockResult.TSCHETNO
        );

        assertEquals(BlockResult.TSCHETNO, blockAttempt.getResult());
    }

    @Test
    public void testBlockAttemptSuccessful() {
        University university = new University("Kruxwan University", "Blue Belt");
        Person intruder = new Person("Intruder", "Scholar", Emotion.ANGRY, university);
        Person guard = new Person("Guard", "Security", Emotion.CALM, null);

        BlockAttempt blockAttempt = new BlockAttempt(
                List.of(guard),
                List.of(intruder),
                BlockResult.SUCCESSFUL
        );

        assertEquals(BlockResult.SUCCESSFUL, blockAttempt.getResult());
    }

    @Test
    public void testEventWithBlockAttempt() {
        University university = new University("Kruxwan University", "Blue Belt");
        Person intruder = new Person("Intruder", "Scholar", Emotion.ANGRY, university);
        Person guard = new Person("Guard", "Security", Emotion.CALM, null);
        Room room = new Room();

        BlockAttempt blockAttempt = new BlockAttempt(
                List.of(guard),
                List.of(intruder),
                BlockResult.SUCCESSFUL
        );

        Event event = new Event(EventType.INVASION, room, "Attempted break-in", blockAttempt);
        event.addParticipant(intruder);
        event.addParticipant(guard);

        assertEquals(BlockResult.SUCCESSFUL, event.getBlockResult());
    }






}
