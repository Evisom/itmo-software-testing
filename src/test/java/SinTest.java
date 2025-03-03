import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.task1;


public class SinTest {
    @ParameterizedTest(name="lalala")
    @DisplayName("Check from Math.Sin")
    @ValueSource(doubles = {0})
    void checkMathSin(double param) {
        assertAll(
                ()-> assertEquals(Math.sin(param), task1.calc_sin(10, param))
        );
    }
}