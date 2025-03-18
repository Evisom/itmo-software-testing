package tpo.lab2.trig;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class CotTest {

    private static final double EPSILON = 0.01;

    @Mock
    private Sin sinMock;

    @Mock
    private Cos cosMock;

    private Cot cot;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на котангенс...");
    }

    @BeforeEach
    void setUp() {
        cot = new Cot(sinMock, cosMock);
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.cos(x) / Math.sin(x)")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathCot(double input) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        // Настройка моков
        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        // Проверка результата
        double result = cot.compute(input, EPSILON);
        assertEquals(cosX / sinX, result, EPSILON);
    }

    @ParameterizedTest
    @DisplayName("Тест на исключение при sin(x) = 0")
    @ValueSource(doubles = {Math.PI, 2 * Math.PI, 3 * Math.PI})
    void checkCotExceptionOnZeroSin(double input) {
        // Настройка моков для случая, когда sin(x) = 0
        when(sinMock.compute(input, EPSILON)).thenReturn(0.0);
        when(cosMock.compute(input, EPSILON)).thenReturn(Math.cos(input));

        // Проверка, что возникает исключение
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> cot.compute(input, EPSILON));
        assertEquals("cot(x) не определён при sin(x) = 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.cos(x) / Math.sin(x)")
    @CsvSource({
            "0, Infinity",
            "Math.PI / 4, 1.0",
            "Math.PI / 2, 0.0",
            "3 * Math.PI / 4, -1.0",
            "Math.PI, 0.0"
    })
    void checkTableValues(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        // Настройка моков
        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        // Проверка результата
        if (sinX != 0.0) {
            double result = cot.compute(input, EPSILON);
            assertEquals(expected, result, EPSILON);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> cot.compute(input, EPSILON));
            assertEquals("cot(x) не определён при sin(x) = 0", exception.getMessage());
        }
    }
}