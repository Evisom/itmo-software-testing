package tpo.lab2.log;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class Log10Test {

    private static final double EPSILON = 0.01;

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
        // Проверка, что возникает исключение при x <= 0
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> log10.compute(0, EPSILON));
        assertEquals("log10(x) не определён для x <= 0", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.log(x) / Math.log(10)")
    @ValueSource(doubles = {0.5, 1.0, 1.5, 2.0, 2.71828, 3.0, 10.0})
    void checkForMathLog10(double input) {
        double lnX = Math.log(input);
        double ln10 = Math.log(10);

        // Настройка моков
        when(lnMock.compute(input, EPSILON)).thenReturn(lnX);
        when(lnMock.compute(10.0, EPSILON)).thenReturn(ln10);

        // Проверка результата
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
        double result = log10.compute(1000000, EPSILON);
        assertEquals(Math.log(1000000) / Math.log(10), result, EPSILON);
    }
}