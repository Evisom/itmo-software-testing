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
public class TanTest {

    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;

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
        assertEquals(sinX / cosX, result, DELTA);
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
            "0.7853981633974483, 1.0",  // Math.PI / 4 = 0.7853981633974483
            "1.5707963267948966, Infinity",  // Math.PI / 2 = 1.5707963267948966
            "2.356194490192345, -1.0",  // 3 * Math.PI / 4 = 2.356194490192345
            "3.141592653589793, 0.0"  // Math.PI = 3.141592653589793
    })
    void checkTableValues(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        if (Math.abs(cosX) > EPSILON) {
            double result = tan.compute(input, EPSILON);
            assertEquals(expected, result, DELTA);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> tan.compute(input, EPSILON));
            assertEquals("tan(x) не определён при cos(x) = 0", exception.getMessage());
        }
    }
}