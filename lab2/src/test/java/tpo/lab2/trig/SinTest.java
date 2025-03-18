package tpo.lab2.trig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {
    private static final double EPSILON = 1e-6;
    private final Sin sin = new Sin();

    @Test
    void testSinAtZero() {
        assertEquals(0.0, sin.compute(0, EPSILON), EPSILON, "sin(0) should be 0");
    }

    @Test
    void testSinAtPiDiv2() {
        assertEquals(Math.sin(Math.PI / 2), sin.compute(Math.PI / 2, EPSILON), EPSILON, "sin(π/2) should be 1");
    }

    @Test
    void testSinAtPi() {
        assertEquals(Math.sin(Math.PI), sin.compute(Math.PI, EPSILON), EPSILON, "sin(π) should be 0");
    }

    @Test
    void testSinAtNegativePiDiv2() {
        assertEquals(Math.sin(-Math.PI / 2), sin.compute(-Math.PI / 2, EPSILON), EPSILON, "sin(-π/2) should be -1");
    }

    @Test
    void testSinAtRandomValue() {
        double x = 1.23;
        assertEquals(Math.sin(x), sin.compute(x, EPSILON), EPSILON, "sin(1.23) should match Math.sin");
    }

    @Test
    void testSinAtLargeValue() {
        double x = 10;
        assertEquals(Math.sin(x), sin.compute(x, EPSILON), EPSILON, "sin(10) should match Math.sin");
    }

    @Test
    void testSinAtVerySmallEpsilon() {
        double x = Math.PI / 6;
        double smallEpsilon = 1e-10;
        assertEquals(Math.sin(x), sin.compute(x, smallEpsilon), smallEpsilon, "sin(π/6) should be precise for small epsilon");
    }


    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.sin()")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathSin(double input) {
        double result = new Sin().compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.sin(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на единицы (для углов кратных π/2)")
    @ValueSource(doubles = {-3 * Math.PI / 2, Math.PI / 2, 5 * Math.PI / 2})
    void checkOnes(double input) {
        double result = new Sin().compute(input, EPSILON);
        assertAll(
                () -> assertEquals(1, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на нули (для углов кратных π)")
    @ValueSource(doubles = {0, Math.PI, 2 * Math.PI, 3 * Math.PI})
    void checkZeroes(double input) {
        double result = new Sin().compute(input, EPSILON);
        assertAll(
                () -> assertEquals(0, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.sin()")
    @ValueSource(doubles = {0, Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2, 5 * Math.PI / 6, 3 * Math.PI / 4, 3 * Math.PI / 3})
    void checkMathSinTable(double input) {
        double result = new Sin().compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.sin(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения (высчитано с помощью точных значений)")
    @CsvSource({
            "0,0.0",
            "0.5235987756,0.5",
            "0.7853981634,0.7071067812",
            "1.0471975512,0.8660254038",
            "1.5707963268,1.0",
            "2.61799387799,0.5",
            "2.35619449019,0.7071067812",
            "3.1415926536,0.0"
    })
    void checkTableValues(double input, double expected) {
        double result = new Sin().compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }
}

