package tpo.lab2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tpo.lab2.log.Log10;
import tpo.lab2.log.Log2;
import tpo.lab2.trig.Cot;
import tpo.lab2.trig.Sin;
import tpo.lab2.trig.Tan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionSystemTest {
    private static final double EPSILON = 0.00001;
    private static final double DELTA = 0.001;

    @Mock
    private Sin sinMock;

    @Mock
    private Cot cotMock;
    @Mock
    private Tan tanMock;
    @Mock
    private Log2 log2Mock;
    @Mock
    private Log10 log10Mock;


    private FunctionSystem fs;

    @BeforeAll
    static void info() {
        System.out.println("Запускаем тесты на систему функций...");
    }

    @BeforeEach
    void setUp() {
        fs = new FunctionSystem(sinMock, tanMock, cotMock, log2Mock, log10Mock);

    }

    @Test
    void testComputeForNegativeX() {
        double x = -1.0;

        when(tanMock.compute(x, EPSILON)).thenReturn(Math.tan(x));
        when(sinMock.compute(x, EPSILON)).thenReturn(Math.sin(x));
        when(cotMock.compute(x, EPSILON)).thenReturn(Math.cos(x));

        double expected = Math.pow(Math.pow((((Math.tan(x) - Math.sin(x)) * Math.sin(x)) - Math.pow(1.0 / Math.tan(x), 2)), 2), 3);
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);


    }

    @ParameterizedTest
    @ValueSource(doubles = {-5.32518, -0.95801})
    void testComputeForNegativeXANDYZero(double x) {


        when(tanMock.compute(x, EPSILON)).thenReturn(Math.tan(x));
        when(sinMock.compute(x, EPSILON)).thenReturn(Math.sin(x));
        when(cotMock.compute(x, EPSILON)).thenReturn(Math.cos(x));

        double expected = Math.pow(Math.pow((((Math.tan(x) - Math.sin(x)) * Math.sin(x)) - Math.pow(1.0 / Math.tan(x), 2)), 2), 3);
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);


    }

    @Test
    void testComputeForPositiveX() {
        double x = 2.0;

        when(log10Mock.compute(x, EPSILON)).thenReturn(Math.log10(x));
        when(log2Mock.compute(x, EPSILON)).thenReturn(Math.log(x) / Math.log(2));

        double log10x = Math.log10(x);
        double log2x = Math.log(x) / Math.log(2);
        double expected = Math.pow(log10x, 4) - log10x - log2x + log10x;
        double actual = fs.compute(x, EPSILON);

        assertEquals(expected, actual, DELTA);


    }

    @ParameterizedTest
    @ValueSource(doubles ={-Math.PI / 2, -Math.PI, -3 * Math.PI / 2, -2 * Math.PI})
    void testComputeAtExcludedPoints(double x) {

            when(tanMock.compute(x, EPSILON)).thenReturn(Double.NaN);
            when(sinMock.compute(x, EPSILON)).thenReturn(Double.NaN);
            when(cotMock.compute(x, EPSILON)).thenReturn(Double.NaN);
            assertEquals(Double.NaN, fs.compute(x, EPSILON));

    }

    @Test
    void testComputeNearZero() {
        double xNeg = -EPSILON;
        double xPos = EPSILON;

        when(tanMock.compute(xNeg, EPSILON)).thenReturn(Math.tan(xNeg));
        when(sinMock.compute(xNeg, EPSILON)).thenReturn(-EPSILON);
        when(cotMock.compute(xNeg, EPSILON)).thenReturn(Math.cos(xNeg)/Math.sin(xNeg));
        assertEquals(Math.pow(Math.pow((((Math.tan(xNeg) - Math.sin(xNeg)) * Math.sin(xNeg)) - Math.pow(1.0 / Math.tan(xNeg), 2)), 2), 3), fs.compute(xNeg, EPSILON), DELTA);

        when(log10Mock.compute(xPos, EPSILON)).thenReturn(Math.log10(xPos));
        when(log2Mock.compute(xPos, EPSILON)).thenReturn(Math.log(xPos) / Math.log(2));
        assertEquals(Math.pow(Math.pow(Math.log10(xPos), 2), 2) - Math.log10(xPos) - Math.log(xPos) / Math.log(2) + Math.log10(xPos), fs.compute(xPos, EPSILON), DELTA);
    }

    @Test
    void testComputeAtLogZeros() {
        double x1 = 1.0;
        when(log10Mock.compute(x1, EPSILON)).thenReturn(Math.log10(x1));
        when(log2Mock.compute(x1, EPSILON)).thenReturn(Math.log(x1) / Math.log(2));
        assertEquals(0.0, fs.compute(x1, EPSILON));
    }

    @Test
    void testComputeAtInflectionPoint() {
        double x = 10.0;
        when(log10Mock.compute(x, EPSILON)).thenReturn(Math.log10(x));
        when(log2Mock.compute(x, EPSILON)).thenReturn(Math.log(x) / Math.log(2));
        assertEquals(Math.pow(Math.log10(x), 4) - Math.log10(x) - Math.log(x) / Math.log(2) + Math.log10(x), fs.compute(x, EPSILON), DELTA);
    }

    @Test
    void testComputeAtSteepDecrease() {
        double x = 0.1;
        when(log10Mock.compute(x, EPSILON)).thenReturn(Math.log10(x));
        when(log2Mock.compute(x, EPSILON)).thenReturn(Math.log(x) / Math.log(2));
        assertEquals(Math.pow(Math.log10(x), 4) - Math.log10(x) - Math.log(x) / Math.log(2) + Math.log10(x), fs.compute(x, EPSILON), DELTA);
    }
}
