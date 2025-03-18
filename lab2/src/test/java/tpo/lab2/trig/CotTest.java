package tpo.lab2.trig;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CotTest {

    private static final double EPSILON = 0.01;
    private static final double DELTA = 0.01;

    @Mock
    private Sin sinMock;

    @Mock
    private Cos cosMock;

    private Cot cot;
    private Cot cotMock;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на котангенс...");
    }

    @BeforeEach
    void setUp() {
        cotMock = new Cot(sinMock, cosMock);
        cot = new Cot(new Sin(), new Cos(new Sin()));
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.cos(x) / Math.sin(x)")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathCotMock(double input) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);
        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        if (Math.abs(sinX) > EPSILON) {
            double result = cotMock.compute(input, EPSILON);
            assertEquals(cosX / sinX, result, DELTA);
        } else {
            assertThrows(ArithmeticException.class, () -> cotMock.compute(input, EPSILON));
        }
    }

    @ParameterizedTest
    @DisplayName("Тест на исключение при sin(x) = 0")
    @ValueSource(doubles = {Math.PI, 2 * Math.PI, 3 * Math.PI})
    void checkCotExceptionOnZeroSin(double input) {

        when(sinMock.compute(input, EPSILON)).thenReturn(0.0);
        when(cosMock.compute(input, EPSILON)).thenReturn(Math.cos(input));

        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> cotMock.compute(input, EPSILON));
        assertEquals("cot(x) не определён при sin(x) = 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.cos(x) / Math.sin(x)")
    @CsvSource({
            "0, Infinity",
            "0.7853981633974483, 1.0",
            "1.5707963267948966, 0.0",
            "2.356194490192345, -1.0",
            "3.141592653589793, 0.0"
    })
    void checkTableValuesMock(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);


        if (Math.abs(sinX - 0.0) > EPSILON) {
            double result = cotMock.compute(input, EPSILON);
            assertEquals(expected, result, DELTA);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> cotMock.compute(input, EPSILON));
            assertEquals("cot(x) не определён при sin(x) = 0", exception.getMessage());
        }
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.cot(x)")
    @ValueSource(doubles = {Math.PI / 4, Math.PI / 3, Math.PI / 6, -Math.PI / 4, -Math.PI / 3, -Math.PI / 6})
    void checkForMathCot(double input) {
        double result = cot.compute(input, EPSILON);
        assertEquals(1.0 / Math.tan(input), result, EPSILON);
    }

    @ParameterizedTest
    @DisplayName("Тест на бесконечность (для x = 0, π, 2π и т.д.)")
    @ValueSource(doubles = {0, Math.PI, 2 * Math.PI, -Math.PI})
    void checkInfinity(double input) {
        assertThrows(ArithmeticException.class, () -> cot.compute(input, EPSILON));
    }

    @ParameterizedTest
    @DisplayName("Тест на единичные значения")
    @ValueSource(doubles = {Math.PI / 4})
    void checkOnes(double input) {
        double result = cot.compute(input, 0.01);
        assertEquals(1.0, result, 0.01);
    }

    @ParameterizedTest
    @DisplayName("Тест на нули (для углов кратных π)")
    @ValueSource(doubles = {Math.PI, 2 * Math.PI, 3 * Math.PI})
    void checkZeroes(double input) {
        assertThrows(ArithmeticException.class, ()-> cot.compute(input, EPSILON));
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения (высчитано с помощью точных значений)")
    @CsvSource({

            "0.78539816339,1",
            "-0.78539816339,-1",
            "1.0471975512,0.57735",
            "0.5235987756,1.73205",

    })
    void checkTableValues(double input, double expected) {
        double result = cot.compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }
}