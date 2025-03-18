package tpo.lab1.function;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {

    private static final int TERMS_COUNT = 7;
    private static final double EPSILON = 0.01;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тестики на синус...");
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.sin()")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathSin(double input) {
        double result =  Sin.calc_sin(TERMS_COUNT, input);
        assertAll(
                ()-> assertEquals(Math.sin(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на единицы")
    @ValueSource(doubles = {-3*Math.PI/2, Math.PI/2, 5*Math.PI/2})
    void checkOnes(double input) {
        double result =  Sin.calc_sin(TERMS_COUNT, input);
        assertAll(
                ()-> assertEquals(1, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на нули")
    @ValueSource(doubles = {0, Math.PI, 2*Math.PI, 3*Math.PI})
    void checkZeroes(double input) {
        double result =  Sin.calc_sin(TERMS_COUNT, input);
        assertAll(
                ()-> assertEquals(0, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.sin()")
    @ValueSource(doubles = {0, Math.PI/6, Math.PI/4, Math.PI/3, Math.PI/2, 5*Math.PI/6, 3*Math.PI/4, 3*Math.PI/3})
    void checkMathSinTable(double input) {
        double result =  Sin.calc_sin(TERMS_COUNT, input);
        assertAll(
                ()-> assertEquals(Math.sin(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения (высчитано с помощью супер-точной ЭВМ)")
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
        double result =  Sin.calc_sin(TERMS_COUNT, input);
        assertEquals(expected, result, EPSILON);
    }
}