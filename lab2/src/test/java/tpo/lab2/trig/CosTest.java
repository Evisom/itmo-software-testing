package tpo.lab2.trig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CosTest {

    @Mock
    private Sin sinMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
}