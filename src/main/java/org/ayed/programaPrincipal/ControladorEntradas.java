package org.ayed.programaPrincipal;
import java.util.Scanner;

public class ControladorEntradas {
    private final Scanner scanner = new Scanner(System.in);
    
    private boolean esOpcionNumericaValida(int opcion, int maximoOpciones) {
        return (Integer) opcion >= 0 && (Integer) opcion <= maximoOpciones;
    }

    /**
     * Lee la entrada del usuario y la retorna como el tipo indicado.
     * Si la conversión falla, solicita al usuario que ingrese un valor válido.
     * @param boolean esNumerica Indica si la entrada es numérica o no.
     * @return <T> La entrada del usuario convertida al tipo indicado (Integer o String).
     */
    @SuppressWarnings("unchecked")
    public <T> T leerEntrada(boolean esNumerica) {
            while (true) {
                String entrada = scanner.nextLine();
    
                if (esNumerica) {
                    try {
                        Integer numero = Integer.valueOf(entrada);
                        return (T) numero;
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Debe ingresar un número.");
                    }
                } else {
                    return (T) entrada;
                }
            }
        }

    /** 
     * Obtiene una opción válida del usuario dentro del rango especificado.
     * @param maximoOpciones El número máximo de opciones disponibles.
     * @return int La opción válida seleccionada por el usuario.
     */
    public int obtenerOpcion(int maximoOpciones) {
        boolean esNumerica = true;
        System.out.println("Seleccione una opción: ");
        int opcion = leerEntrada(esNumerica);

        while (!esOpcionNumericaValida(opcion, maximoOpciones)) {
            System.out.println("Opción inválida. Por favor, ingrese una opción válida:");
            opcion = leerEntrada(esNumerica);
            System.out.println("\n");
        }
        return opcion;
    }

}
