package tpo.lab2.log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class LnTest {

    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;
    private Ln ln;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на натуральный логарифм...");
    }

    @BeforeEach
    void setUp() {
        ln = new Ln();
    }

    @Test
    @DisplayName("Тест на исключение при x <= 0")
    void checkLnExceptionForNonPositiveX() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ln.compute(0, EPSILON));
        assertEquals("ln(x) не определён для x <= 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.log(x)")
    @ValueSource(doubles = {0.5, 1.0, 1.5, 2.0, 2.71828, 3.0, 10.0})
    void checkForMathLn(double input) {
        double result = ln.compute(input, EPSILON);
        assertEquals(Math.log(input), result, DELTA);
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.log(x)")
    @CsvSource({
            "1, 0.0",
            "2.718, 1.0",
            "2, 0.69314718056",
            "3, 1.09861228844",
            "10, 2.30258509299"
    })
    void checkTableValues(double input, double expected) {
        double result = ln.compute(input, EPSILON);
        assertEquals(expected, result, DELTA);
    }

    @Test
    @DisplayName("Тест на вычисление логарифма для значений меньше 1")
    void checkLnForLessThanOne() {
        double result = ln.compute(0.5, EPSILON);
        assertTrue(result < 0, "Результат должен быть отрицательным для x < 1");
    }

    @Test
    @DisplayName("Тест на корректную работу с числами большого размера")
    void checkLnForLargeNumber() {
        double result = ln.compute(1000000, EPSILON);
        assertEquals(Math.log(1000000), result, DELTA);
    }
}