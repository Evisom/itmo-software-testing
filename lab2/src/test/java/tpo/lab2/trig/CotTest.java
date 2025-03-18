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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CotTest {

    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;

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

        // Проверка для значений, где sin(x) не равен 0
        if (Math.abs(sinX) > EPSILON) {
            double result = cot.compute(input, EPSILON);
            assertEquals(cosX / sinX, result, DELTA);
        } else {
            // Проверка, что исключение выбрасывается при sin(x) == 0
            assertThrows(ArithmeticException.class, () -> cot.compute(input, EPSILON));
        }
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
            "0.7853981633974483, 1.0",  // Math.PI / 4 = 0.7853981633974483
            "1.5707963267948966, 0.0",  // Math.PI / 2 = 1.5707963267948966
            "2.356194490192345, -1.0",  // 3 * Math.PI / 4 = 2.356194490192345
            "3.141592653589793, 0.0"    // Math.PI = 3.141592653589793
    })
    void checkTableValues(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        // Настройка моков
        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        // Проверка результата
        if (Math.abs(sinX - 0.0) > EPSILON) {
            double result = cot.compute(input, EPSILON);
            assertEquals(expected, result, DELTA);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> cot.compute(input, EPSILON));
            assertEquals("cot(x) не определён при sin(x) = 0", exception.getMessage());
        }
    }
}