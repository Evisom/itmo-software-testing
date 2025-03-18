package tpo.lab2.trig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


@ExtendWith(MockitoExtension.class)
class CosTest {

    private static final double EPSILON = 1e-6;

    @Mock
    private Sin sinMock;
    private Cos cos;

    @BeforeEach
    void setUp() {
        openMocks(this);
        cos = new Cos(new Sin());
    }

    @Test
    void testCompute() {
        double x = Math.PI / 4;
        double eps = 0.0001;
        when(sinMock.compute(Math.PI / 2 - x, eps)).thenReturn(Math.cos(x));
        Cos cos = new Cos(sinMock);
        double result = cos.compute(x, eps);
        assertEquals(Math.cos(x), result, eps);
        verify(sinMock).compute(Math.PI / 2 - x, eps);
    }

    @Test
    void testComputeZero() {
        double eps = 0.0001;
        when(sinMock.compute(Math.PI / 2, eps)).thenReturn(Math.cos(0));
        Cos cos = new Cos(sinMock);
        assertEquals(Math.cos(0), cos.compute(0, eps), eps);
        verify(sinMock).compute(Math.PI / 2, eps);
    }

    @Test
    void testComputePi() {
        double eps = 0.0001;
        when(sinMock.compute(-Math.PI / 2, eps)).thenReturn(Math.cos(Math.PI));
        Cos cos = new Cos(sinMock);
        assertEquals(Math.cos(Math.PI), cos.compute(Math.PI, eps), eps);
        verify(sinMock).compute(-Math.PI / 2, eps);
    }

    @Test
    void testComputeNegative() {
        double x = -Math.PI / 3;
        double eps = 0.0001;
        when(sinMock.compute(Math.PI / 2 - x, eps)).thenReturn(Math.cos(x));
        Cos cos = new Cos(sinMock);
        assertEquals(Math.cos(x), cos.compute(x, eps), eps);
        verify(sinMock).compute(Math.PI / 2 - x, eps);
    }

    @Test
    void testComputeLargeValue() {
        double x = 10 * Math.PI;
        double eps = 0.0001;
        when(sinMock.compute(Math.PI / 2 - x, eps)).thenReturn(Math.cos(x));
        Cos cos = new Cos(sinMock);
        assertEquals(Math.cos(x), cos.compute(x, eps), eps);
        verify(sinMock).compute(Math.PI / 2 - x, eps);
    }



    @ParameterizedTest
    @DisplayName("Тест на соответствие с Math.cos()")
    @ValueSource(doubles = {0, Math.PI / 4, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI})
    void checkForMathCos(double input) {
        double result = cos.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.cos(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на единицы (для углов кратных 2π)")
    @ValueSource(doubles = {0, 2 * Math.PI})
    void checkOnes(double input) {
        double result = cos.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(1.0, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на нули (для углов кратных π/2)")
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2})
    void checkZeroes(double input) {
        double result = cos.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(0.0, result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения с Math.cos()")
    @ValueSource(doubles = {0, Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2, 5 * Math.PI / 6, 3 * Math.PI / 4, 3 * Math.PI / 3})
    void checkMathCosTable(double input) {
        double result = cos.compute(input, EPSILON);
        assertAll(
                () -> assertEquals(Math.cos(input), result, EPSILON)
        );
    }

    @ParameterizedTest
    @DisplayName("Тест на табличные значения (высчитано с помощью супер-точной ЭВМ)")
    @CsvSource({
            "0,1.0",
            "0.5235987756,0.8660254038",
            "0.7853981634,0.7071067812",
            "1.0471975512,0.5000000000173335",
            "1.5707963268,0.0",
            "2.61799387799,-0.8660254034927357",
            "2.35619449019,-0.7071067829352091",
            "3.1415926536,-1.0"
    })
    void checkTableValues(double input, double expected) {
        double result = cos.compute(input, EPSILON);
        assertEquals(expected, result, EPSILON);
    }
}