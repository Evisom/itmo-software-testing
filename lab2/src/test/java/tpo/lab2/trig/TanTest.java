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
public class TanTest {

    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;

    @Mock
    private Sin sinMock;

    @Mock
    private Cos cosMock;

    private Tan tan;
    private Tan mockTan;
    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на тангенс...");
    }

    @BeforeEach
    void setUp() {
        mockTan = new Tan(sinMock, cosMock);
        tan = new Tan(new Sin(), new Cos(new Sin()));
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.sin(x) / Math.cos(x)")
    @ValueSource(doubles = {0, 1, 0.5, 0.3, 0.2, 0.6, -1, -0.6, -0.4, -0.3})
    void checkForMathTanMock(double input) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);


        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);


        double result = mockTan.compute(input, EPSILON);
        assertEquals(sinX / cosX, result, DELTA);
    }

    @ParameterizedTest
    @DisplayName("Тест на исключение при cos(x) = 0")
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2})
    void checkTanExceptionOnZeroCosMock(double input) {

        when(sinMock.compute(input, EPSILON)).thenReturn(Math.sin(input));
        when(cosMock.compute(input, EPSILON)).thenReturn(0.0);

        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> mockTan.compute(input, EPSILON));
        assertEquals("tan(x) не определён при cos(x) = 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.sin(x) / Math.cos(x)")
    @CsvSource({
            "0, 0.0",
            "0.7853981633974483, 1.0",
            "1.5707963267948966, Infinity",
            "2.356194490192345, -1.0",
            "3.141592653589793, 0.0"
    })
    void checkTableValuesMock(double input, double expected) {
        double sinX = Math.sin(input);
        double cosX = Math.cos(input);

        when(sinMock.compute(input, EPSILON)).thenReturn(sinX);
        when(cosMock.compute(input, EPSILON)).thenReturn(cosX);

        if (Math.abs(cosX) > EPSILON) {
            double result = mockTan.compute(input, EPSILON);
            assertEquals(expected, result, DELTA);
        } else {
            ArithmeticException exception = assertThrows(ArithmeticException.class, () -> mockTan.compute(input, EPSILON));
            assertEquals("tan(x) не определён при cos(x) = 0", exception.getMessage());
        }
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.tan()")
    @ValueSource(doubles = {0, Math.PI / 4, Math.PI, 2 * Math.PI})
    void checkForMathTan(double input) {
        double result = tan.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.tan(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на бесконечность (для x = π/2, 3π/2, и т.д.)")
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2})
    void checkInfinity(double input) {
        assertThrows(ArithmeticException.class, () -> tan.compute(input, EPSILON));
    }

    @ParameterizedTest
    @DisplayName("Тест на нули (для углов кратных π)")
    @ValueSource(doubles = {0, Math.PI, 2 * Math.PI, 3 * Math.PI})
    void checkZeroes(double input) {
        double result = tan.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(0.0, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.tan()")
    @ValueSource(doubles = {0, Math.PI / 4, Math.PI / 3, Math.PI / 6})
    void checkMathTanTable(double input) {
        double result = tan.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.tan(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения (высчитано с помощью супер-точной ЭВМ)")
    @CsvSource({
            "0,0.0",
            "0.5235987756,0.5773502692",
            "0.7853981634,1.0",
            "1.0471975512,1.7320508076",
            "2.61799387799,-0.5773502692",
            "3.1415926536,0.0"
    })
    void checkTableValues(double input, double expected) {
        double result = tan.compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }
}