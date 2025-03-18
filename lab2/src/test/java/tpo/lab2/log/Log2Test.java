package tpo.lab2.log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class Log2Test {

    private static final double EPSILON = 0.01;

    @Mock
    private Ln lnMock;

    private Log2 log2;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на логарифм по основанию 2...");
    }

    @BeforeEach
    void setUp() {
        log2 = new Log2(lnMock);
    }

    @Test
    @DisplayName("Тест на исключение при x <= 0")
    void checkLog2ExceptionForNonPositiveX() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> log2.compute(0, EPSILON));
        assertEquals("log2(x) не определён для x <= 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.log(x) / Math.log(2)")
    @ValueSource(doubles = {0.5, 1.0, 1.5, 2.0, 2.71828, 3.0, 10.0})
    void checkForMathLog2(double input) {
        double lnX = Math.log(input);
        double ln2 = Math.log(2);

        when(lnMock.compute(input, EPSILON)).thenReturn(lnX);
        when(lnMock.compute(2.0, EPSILON)).thenReturn(ln2);

        double result = log2.compute(input, EPSILON);
        assertEquals(lnX / ln2, result, EPSILON);
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.log(x) / Math.log(2)")
    @CsvSource({
            "1, 0.0",
            "2, 1.0",
            "4, 2.0",
            "8, 3.0",
            "16, 4.0"
    })
    void checkTableValues(double input, double expected) {
        double lnX = Math.log(input);
        double ln2 = Math.log(2);

        when(lnMock.compute(input, EPSILON)).thenReturn(lnX);
        when(lnMock.compute(2.0, EPSILON)).thenReturn(ln2);

        double result = log2.compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }

    @Test
    @DisplayName("Тест на корректную работу с числами большого размера")
    void checkLog2ForLargeNumber() {
        double result = log2.compute(1000000, EPSILON);
        assertEquals(Math.log(1000000) / Math.log(2), result, EPSILON);
    }





    @Test
    void testLog2PositiveValue() {
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);

        double result = log2.compute(8, 1e-6);
        assertEquals(3.0, result, 1e-6, "log2(8) должно быть 3");
    }

    @Test
    void testLog2SmallValue() {
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);

        double result = log2.compute(0.5, 1e-6);
        assertEquals(-1.0, result, 1e-6, "log2(0.5) должно быть -1");
    }

    @Test
    void testLog2ThrowsExceptionForNonPositiveArgument() {
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);

        assertThrows(IllegalArgumentException.class, () -> log2.compute(0, 1e-6), "log2(0) должен выбросить исключение");
        assertThrows(IllegalArgumentException.class, () -> log2.compute(-1, 1e-6), "log2(-1) должен выбросить исключение");
    }

    @Test
    void testLog2WithVerySmallValue() {
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);

        double result = log2.compute(1e-6, 1e-6);
        assertTrue(result < 0, "log2(1e-6) должно быть отрицательным");
    }

    @Test
    void testLog2WithLargeValue() {
        Ln ln = new Ln();
        Log2 log2 = new Log2(ln);

        double result = log2.compute(1000, 1e-6);
        assertTrue(result > 0, "log2(1000) должно быть положительным");
    }
}
