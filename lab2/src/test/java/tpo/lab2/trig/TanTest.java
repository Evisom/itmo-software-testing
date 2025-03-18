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

public class TanTest {

    private static final double EPSILON = 0.01;

    @Mock
    private Sin sinMock;

    @Mock
    private Cos cosMock;

    private Tan tan;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на тангенс...");
    }

    @BeforeEach
    void setUp() {
        tan = new Tan(sinMock, cosMock);
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.sin(x) / Math.cos(x)")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathTan(double input) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);


        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);


        double result = tan.compute(input, EPSILON);
        assertEquals(sinX / cosX, result, EPSILON);
    }

    @ParameterizedTest
    @DisplayName("Тест на исключение при cos(x) = 0")
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2})
    void checkTanExceptionOnZeroCos(double input) {

        when(sinMock.compute(input, EPSILON)).thenReturn(Math.sin(input));
        when(cosMock.compute(input, EPSILON)).thenReturn(0.0);


        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> tan.compute(input, EPSILON));
        assertEquals("tan(x) не определён при cos(x) = 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.sin(x) / Math.cos(x)")
    @CsvSource({
            "0, 0.0",
            "Math.PI / 4, 1.0",
            "Math.PI / 2, Infinity",
            "3 * Math.PI / 4, -1.0",
            "Math.PI, 0.0"
    })
    void checkTableValues(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);


        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);


        if (Math.abs(cosX) > EPSILON) {
            double result = tan.compute(input, EPSILON);
            assertEquals(expected, result, EPSILON);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> tan.compute(input, EPSILON));
            assertEquals("tan(x) не определён при cos(x) = 0", exception.getMessage());
        }
    }
}