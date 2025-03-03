import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.task1;


public class SinTest {

    private static final int TERMS_COUNT = 10;
    private static final double EPSILON = 0.01;

    @BeforeAll
    static void setup() {
        System.out.println("Запускаем тестики на синус...");
    }


    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.sin()")
    @ValueSource(doubles = {0, 1})
    void checkMathSin(double input) {
        double result =  task1.calc_sin(TERMS_COUNT, input);
        assertAll(
                ()-> assertEquals(Math.sin(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения")
    @CsvSource({
            "0, 0",
            "1.57079632679, 1",
            "3.14159265359, 0",
            "4.71238898038, -1",
            "6.28318530718, 0"
    })
    void checkTableValues(double input, double expected) {
        double result =  task1.calc_sin(TERMS_COUNT, input);
        assertEquals(expected, result, EPSILON);
    }
}