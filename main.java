/**
 * HDT2 - Calculadora Postfix
 * @author Anthony Portillo 25615, Alejandro Rustrian 25512
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Calculator calc = new Calculator();
        Stack<Integer> stack = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                // asumir que la línea es una expresión en notación infix (tokens separados por espacios)
                System.out.println("Expresión infix: " + line);
                String postfix = infixToPostfix(line);
                System.out.println("Postfix equivalente: " + postfix);
                int result = evaluarPostfix(postfix, stack, calc);
                System.out.println("Resultado: " + result);
                System.out.println("--------------------");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static int evaluarPostfix(String expr, Stack<Integer> stack, Calculator calc) {

        /** limpiar la pila por si se reutiliza */
        while (stack.size() > 0) {
            stack.pop();
        }

        String[] tokens = expr.split(" ");

        for (String token : tokens) {

            /** operando */
            if (Character.isDigit(token.charAt(0))) {
                stack.push(Integer.parseInt(token));
            }
            
            /** Operador */
            else {
                int b = stack.pop();
                int a = stack.pop();

                int result;
                switch (token) {
                    case "+" -> result = calc.sumar(a, b);
                    case "-" -> result = calc.restar(a, b);
                    case "*" -> result = calc.multiplicar(a, b);
                    case "/" -> result = calc.dividir(a, b);
                    default -> throw new IllegalArgumentException("Operador inválido: " + token);
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }

    /**
     * Convierte una expresión infix (con tokens separados por espacios) a una cadena postfix.
     */
    private static String infixToPostfix(String expr) {
        Stack<String> ops = new Vector<>();
        StringBuilder out = new StringBuilder();

        String[] tokens = expr.split(" ");
        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0))) {
                out.append(token).append(" ");
            } else if (token.equals("(")) {
                ops.push(token);
            } else if (token.equals(")")) {
                while (ops.size() > 0 && !ops.peek().equals("(")) {
                    out.append(ops.pop()).append(" ");
                }
                if (ops.size() == 0 || !ops.peek().equals("(")) {
                    throw new IllegalArgumentException("Paréntesis desbalanceados");
                }
                ops.pop(); // sacar '('
            } else {
                // operador
                while (ops.size() > 0 && precedence(ops.peek()) >= precedence(token)) {
                    out.append(ops.pop()).append(" ");
                }
                ops.push(token);
            }
        }

        while (ops.size() > 0) {
            String op = ops.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new IllegalArgumentException("Paréntesis desbalanceados");
            }
            out.append(op).append(" ");
        }

        return out.toString().trim();
    }

    private static int precedence(String op) {
        return switch (op) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> 0;
        };
    }
}