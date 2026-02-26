import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class HDT4Tests {

    @Test
    void calculatorOperaCorrectamente() {
        Calculator calc = new Calculator();

        assertEquals(5, calc.sumar(2, 3));
        assertEquals(6, calc.multiplicar(2, 3));
        assertEquals(4, calc.restar(7, 3));
        assertEquals(4, calc.dividir(8, 2));
    }

    @Test
    void calculatorDividirPorCeroLanzaExcepcion() {
        Calculator calc = new Calculator();

        assertThrows(IllegalArgumentException.class, () -> calc.dividir(8, 0));
    }

    @Test
    void vectorStackMantieneOrdenLifoYSize() {
        Stack<Integer> stack = new Vector<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(1, stack.size());
        assertEquals(10, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    void vectorPeekNoRemueveElemento() {
        Stack<String> stack = new Vector<>();
        stack.push("A");
        stack.push("B");

        assertEquals("B", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void vectorPopYPeekEnVaciaLanzanExcepcion() {
        Stack<Integer> stack = new Vector<>();

        assertThrows(IllegalStateException.class, stack::pop);
        assertThrows(IllegalStateException.class, stack::peek);
    }

    @Test
    void vectorCreceDinamicamente() {
        Stack<Integer> stack = new Vector<>();

        for (int i = 0; i < 25; i++) {
            stack.push(i);
        }

        assertEquals(25, stack.size());
        assertEquals(24, stack.peek());
        assertEquals(24, stack.pop());
        assertEquals(23, stack.peek());
    }
}
