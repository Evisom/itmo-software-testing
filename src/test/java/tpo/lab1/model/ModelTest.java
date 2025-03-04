package tpo.lab1.model;

import tpo.lab1.model.enums.ClothingType;
import tpo.lab1.model.enums.Emotion;
import tpo.lab1.model.enums.EventType;
import tpo.lab1.model.enums.LockStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
        event = new Event(EventType.INVASION, room, "Маги ворвались в зал");
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
        Event testEvent = new Event(eventType, room, "Тестовое событие");
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
}
