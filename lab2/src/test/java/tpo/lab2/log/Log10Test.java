package tpo.lab2.log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Log10Test {

    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;

    @Mock
    private Ln lnMock;

    private Log10 log10;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на логарифм по основанию 10...");
    }

    @BeforeEach
    void setUp() {
        log10 = new Log10(lnMock);
    }

    @Test
    @DisplayName("Тест на исключение при x <= 0")
    void checkLog10ExceptionForNonPositiveX() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> log10.compute(0, EPSILON));
        assertEquals("log10(x) не определён для x <= 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.log(x) / Math.log(10)")
    @ValueSource(doubles = {0.5, 1.0, 1.5, 2.0, 2.71828, 3.0, 10.0})
    void checkForMathLog10(double input) {
        double lnX = Math.log(input);
        double ln10 = Math.log(10);

        when(lnMock.compute(input, EPSILON)).thenReturn(lnX);
        when(lnMock.compute(10.0, EPSILON)).thenReturn(ln10);

        double result = log10.compute(input, EPSILON);
        assertEquals(lnX / ln10, result, EPSILON);
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.log(x) / Math.log(10)")
    @CsvSource({
            "1, 0.0",
            "10, 1.0",
            "100, 2.0",
            "1000, 3.0",
            "10000, 4.0"
    })
    void checkTableValues(double input, double expected) {
        double lnX = Math.log(input);
        double ln10 = Math.log(10);

        when(lnMock.compute(input, EPSILON)).thenReturn(lnX);
        when(lnMock.compute(10.0, EPSILON)).thenReturn(ln10);

        double result = log10.compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Тест на корректную работу с числами большого размера")
    void checkLog10ForLargeNumber() {

        when(lnMock.compute(1000000, EPSILON)).thenReturn(Math.log(1000000));
        when(lnMock.compute(10.0, EPSILON)).thenReturn(Math.log(10));


        double result = log10.compute(1000000, EPSILON);


        double expected = Math.log(1000000) / Math.log(10);
        assertEquals(expected, result, DELTA);
    }

    @Test
    void testLog10PositiveValue() {
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);

        double result = log10.compute(1000, 1e-6);
        assertEquals(3.0, result, 1e-6, "log10(1000) должно быть 3");
    }

    @Test
    void testLog10SmallValue() {
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);

        double result = log10.compute(0.1, 1e-6);
        assertEquals(-1.0, result, 1e-6, "log10(0.1) должно быть -1");
    }

    @Test
    void testLog10ThrowsExceptionForNonPositiveArgument() {
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);

        assertThrows(IllegalArgumentException.class, () -> log10.compute(0, 1e-6), "log10(0) должен выбросить исключение");
        assertThrows(IllegalArgumentException.class, () -> log10.compute(-1, 1e-6), "log10(-1) должен выбросить исключение");
    }

    @Test
    void testLog10WithVerySmallValue() {
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);

        double result = log10.compute(1e-6, 1e-6);
        assertTrue(result < 0, "log10(1e-6) должно быть отрицательным");
    }

    @Test
    void testLog10WithLargeValue() {
        Ln ln = new Ln();
        Log10 log10 = new Log10(ln);

        double result = log10.compute(100000, 1e-6);
        assertTrue(result > 0, "log10(100000) должно быть положительным");
    }
}