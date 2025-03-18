package tpo.lab1.algoritm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class SkewHeapTest {

    private SkewHeap heap;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тестики на кучу...");
    }

    @BeforeEach
    void setup() {
        heap = new SkewHeap();
    }

    @ParameterizedTest
    @DisplayName("Вставка и удаление элементов из кучи")
    @CsvSource({
            "10, 5, 20, 5, 10, 20",
            "3, 1, 4, 1, 3, 4",
            "7, 6, 5, 5, 6, 7"
    })

    void testInsertAndExtract(int a, int b, int c, int min1, int min2, int min3) {
        heap.insert(a);
        heap.insert(b);
        heap.insert(c);

        assertEquals(min1, heap.extractMin());
        assertEquals(min2, heap.extractMin());
        assertEquals(min3, heap.extractMin());
    }


    @Test
    @DisplayName("Извлечение из пустой кучи")
    void testExtractFromEmptyHeap() {
        Exception exception = assertThrows(IllegalStateException.class, heap::extractMin);
        assertEquals("Heap is empty", exception.getMessage());
    }


    @Test
    @DisplayName("Вставка одного элемента")
    void testSingleElement() {
        heap.insert(42);
        assertEquals(42, heap.extractMin());
        assertTrue(heap.isEmpty());
    }


    @Test
    @DisplayName("Работа с минимальными и максимальными значениями")
    void testExtremeValues() {
        heap.insert(Integer.MIN_VALUE);
        heap.insert(0);
        heap.insert(Integer.MAX_VALUE);

        assertEquals(Integer.MIN_VALUE, heap.extractMin());
        assertEquals(0, heap.extractMin());
        assertEquals(Integer.MAX_VALUE, heap.extractMin());
    }


    @ParameterizedTest
    @CsvSource({
            "'3,1', '4,2', '1,2,3,4'",
            "'10,5', '8,6', '5,6,8,10'",
            "'15,7,9', '3,20,11', '3,7,9,11,15,20'"
    })
    @DisplayName("Объединение двух куч")
    void testMergeTwoHeaps(String values1Str, String values2Str, String expectedOrderStr) {
        SkewHeap heap1 = new SkewHeap();
        SkewHeap heap2 = new SkewHeap();

        int[] values1 = Arrays.stream(values1Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] values2 = Arrays.stream(values2Str.split(",")).mapToInt(Integer::parseInt).toArray();
        int[] expectedOrder = Arrays.stream(expectedOrderStr.split(",")).mapToInt(Integer::parseInt).toArray();

        for (int value : values1) heap1.insert(value);
        for (int value : values2) heap2.insert(value);

        heap1.mergeWith(heap2);

        for (int expected : expectedOrder) {
            assertEquals(expected, heap1.extractMin());
        }
    }


    @Test
    @DisplayName("Тест с 10,000 случайных элементов")
    void testLargeDataSet() {
        Random random = new Random();
        int[] values = IntStream.generate(() -> random.nextInt(1_000_000))
                .limit(10_000)
                .sorted()
                .toArray();

        for (int value : values) {
            heap.insert(value);
        }

        for (int expected : values) {
            assertEquals(expected, heap.extractMin());
        }
    }


    @Test
    @DisplayName("Тест на корректную сортировку")
    void testSequentialInsertAndExtract() {
        heap.insert(7);
        heap.insert(6);
        heap.insert(5);
        heap.insert(4);
        heap.insert(3);

        assertEquals(3, heap.extractMin());
        assertEquals(4, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(6, heap.extractMin());
        assertEquals(7, heap.extractMin());
    }


    @Test
    @DisplayName("Тест с дубликатами значений")
    void testDuplicates() {
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertTrue(heap.isEmpty());
    }


    @Test
    @DisplayName("Проверка isEmpty()")
    void testIsEmpty() {
        assertTrue(heap.isEmpty());
        heap.insert(10);
        assertFalse(heap.isEmpty());
        heap.extractMin();
        assertTrue(heap.isEmpty());
    }


    @Test
    @DisplayName("Слияние с пустой кучей")
    void testMergeWithEmptyHeap() {
        SkewHeap otherHeap = new SkewHeap();
        heap.insert(10);
        heap.insert(20);

        heap.mergeWith(otherHeap);
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertTrue(heap.isEmpty());
    }
}
