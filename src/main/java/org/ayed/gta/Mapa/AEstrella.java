package org.ayed.gta.Mapa;
import java.util.*;
import org.ayed.tda.grafo.ExcepcionGrafo;
import org.ayed.tda.grafo.Grafo;
import org.ayed.tda.grafo.Heuristica;
import org.ayed.tda.lista.Lista;
import org.ayed.tda.lista.Pila;

/**
 * Implementación del algoritmo AEstrella (A*)
 * Devuelve una lista con los vertices desde origen hasta destino (incluyendolos)
 * Si no hay camino devuelve una Lista vacia
 */
public class AEstrella<T> {
    private final Grafo<T> grafo;
    private final Heuristica<T> heuristica;
    private static final int INF = Integer.MAX_VALUE/ 4;

    /**
     * Constructor de AEstrella (A*)
     * 
     * @param grafo grafo para implementar el algoritmo
     * @param heuristica heuristica que usamos en el algoritmo
     * 
     * @throws ExcepcionGrafo si el grafo es nulo
     * @throws IllegalArgumentException si la heuristica es nula
     */
    public AEstrella(Grafo<T> grafo, Heuristica<T> heuristica) {
        if (grafo == null) throw new ExcepcionGrafo("El grafo no puede ser nulo");
        if (heuristica == null) throw new IllegalArgumentException("La heurística no puede ser nula");
        this.grafo = grafo;
        this.heuristica = heuristica;
    }

    /**
     * Busca el camino de menor costo entre entrada y salida usando A*
     *
     * @param entrada vertice de entrada
     * @param salida vertice final (objetivo)
     * @return Lista<T> con el camino desde entrada hasta salida (incluidos)
     */
    public Lista<T> buscarCamino(T entrada, T salida) {
        Set<T> Sa = new HashSet<>();
        Set<T> Sc = new HashSet<>();

        Map<T, Integer> costoDesdeElInicio = new HashMap<>();
        Map<T, Integer> costoEstimadoTotal = new HashMap<>();

        Map<T, T> provieneDe = new HashMap<>(); //mapa para reconstruir camino

        Sa.add(entrada);
        costoDesdeElInicio.put(entrada, 0);
        costoEstimadoTotal.put(entrada, heuristica.calcularPuntaje(entrada, salida));

        while (!Sa.isEmpty()) {
            T actual = verticeMasPrometedor(Sa, costoEstimadoTotal); //obtenemos el nodo en Sa con menor costoEstimadoTotal

            if (actual.equals(salida)) {
                return reconstruirCamino(provieneDe, actual);
            }

            Sa.remove(actual);
            Sc.add(actual);

            Map<T, Integer> vecinos;
            try {
                vecinos = grafo.obtenerAdyacentes(actual);
            } catch (ExcepcionGrafo e) {
                continue;
            }

            for (Map.Entry<T, Integer> inicio : vecinos.entrySet()) {
                T vecino = inicio.getKey();
                int pesoArista = inicio.getValue();

                if (Sc.contains(vecino)) {
                    continue;
                }

                int costo= costoDesdeElInicio.getOrDefault(actual, INF) + pesoArista;

                boolean mejorCamino= false;
                if (!Sa.contains(vecino)) {
                    Sa.add(vecino); //descubrimos un nuevo nodo
                    mejorCamino= true;
                } else if (costo < costoDesdeElInicio.getOrDefault(vecino, INF)) {
                    mejorCamino= true;
                }

                if (mejorCamino) {
                    provieneDe.put(vecino, actual);
                    costoDesdeElInicio.put(vecino, costo);
                    int f = costo + heuristica.calcularPuntaje(vecino, salida);
                    costoEstimadoTotal.put(vecino, f);
                }
            }
        }
        return new Lista<>(); //Si termina el while sin encontrar la salida, no hay camino
    }

    /**
     * Reconstruye el camino desde entrada hasta actual
     * 
     * @return camino como una Lista<T>
     */
    private Lista<T> reconstruirCamino(Map<T, T> provieneDe, T actual) {
    Pila<T> pila = new Pila<>();
        T nodo = actual;
        while (nodo != null) {
            pila.agregar(nodo);
            nodo= provieneDe.get(nodo);
        }
        Lista<T> camino= new Lista<>();

        while (!pila.vacio()) {
            camino.agregar(pila.eliminar());
        }
        return camino;
    }


    /**
     * Encuentra en el conjunto Sa el vértice con menor costoEstimadoTotal
     * Si un vértice no tiene costoEstimadoTotal definido, se asume INF
     * 
     * @return mejor el vertice mas prometedor
     */
    private T verticeMasPrometedor(Set<T> Sa, Map<T, Integer> costoEstimadoTotal) {
        T mejor = null;
        int mejorF = INF;
        for (T v : Sa) {
            int f = costoEstimadoTotal.getOrDefault(v, INF);
            if (mejor == null || f < mejorF) {
                mejor = v;
                mejorF = f;
            }
        }
        return mejor;
    }

    /**
     * Implementación de la Heuristica Manhattan
     */
    public static class ManhattanHeuristicaCoordenadas implements Heuristica<Coordenadas> {
    @Override
    public int calcularPuntaje(Coordenadas a, Coordenadas b) {
        return Math.abs(a.obtenerX() - b.obtenerX()) + Math.abs(a.obtenerY() - b.obtenerY());
    }
}
}