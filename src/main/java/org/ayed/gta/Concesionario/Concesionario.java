package org.ayed.gta.Concesionario;

import org.ayed.gta.Vehiculos.Exotico;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.Partida;
import org.ayed.tda.vector.Vector;

/**
 * Clase que representa un concesionario de vehículos.
 * Permite agregar, buscar y comprar vehículos.
 */
public class Concesionario {

    private Vector<Vehiculo> stock;

    /**
     * Crea un concesionario vacío.
     */
    public Concesionario() {
        stock = new Vector<>();
    }

    /**
     * Agrega un vehículo al stock.
     *
     * @param vehiculo vehículo a agregar (puede ser normal o exótico).
     * @throws IllegalArgumentException si el vehículo es null.
     */
    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser null");
        }
        stock.agregar(vehiculo);
    }

    /**
     * Busca vehículos por nombre usando coincidencia parcial.
     *
     * @param nombre texto a buscar dentro del nombre del vehículo.
     * @return vector con los vehículos encontrados, vacío si no hay coincidencias.
     * @throws IllegalArgumentException si el parámetro es null.
     */
    public Vector<Vehiculo> buscarPorNombre(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser null");
        }

        Vector<Vehiculo> resultado = new Vector<>();
        // si viene vacío, no hay nada que buscar
        if (nombre.isEmpty()) {
            return resultado;
        }

        String buscado = nombre.toLowerCase();

        // recorro el stock buscando coincidencias parciales
        for (int i = 0; i < stock.tamanio(); i++) {
            Vehiculo vehiculo = stock.dato(i);

            // si el nombre contiene el texto buscado, lo agrego
            if (vehiculo.nombreVehiculo().toLowerCase().contains(buscado)) {
                resultado.agregar(vehiculo);
            }
        }

        return resultado;
    }

    /**
     * Busca vehículos por marca usando coincidencia parcial.
     *
     * @param marca texto a buscar dentro de la marca del vehículo.
     * @return vector con los vehículos encontrados, vacío si no hay coincidencias.
     * @throws IllegalArgumentException si el parámetro es null.
     */
    public Vector<Vehiculo> buscarPorMarca(String marca) {
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser null");
        }

        Vector<Vehiculo> resultado = new Vector<>();
        // si viene vacío, no hay nada que buscar
        if (marca.isEmpty()) {
            return resultado;
        }

        String buscado = marca.toLowerCase();

        // recorro todo el stock
        for (int i = 0; i < stock.tamanio(); i++) {
            Vehiculo vehiculo = stock.dato(i);

            // comparo usando contains para coincidencia parcial
            if (vehiculo.marcaVehiculo().toLowerCase().contains(buscado)) {
                resultado.agregar(vehiculo);
            }
        }

        return resultado;
    }

    /**
     * Permite comprar un vehículo por nombre exacto.
     * No se venden vehículos exóticos y se requiere dinero suficiente.
     *
     * @param nombreExactoVehiculo nombre exacto del vehículo a comprar.
     * @param jugador jugador que realiza la compra.
     * @return true si la compra fue exitosa, false en caso contrario.
     * @throws IllegalArgumentException si nombreExactoVehiculo o jugador son null.
     */
    public boolean comprar(String nombreExactoVehiculo, Partida jugador) {
        if (nombreExactoVehiculo == null) {
            throw new IllegalArgumentException("El nombre no puede ser null");
        }
        if (jugador == null) {
            throw new IllegalArgumentException("El jugador no puede ser null");
        }

        // busco un vehículo con el nombre exacto
        for (int i = 0; i < stock.tamanio(); i++) {
            Vehiculo vehiculo = stock.dato(i);

            if (vehiculo.nombreVehiculo().equalsIgnoreCase(nombreExactoVehiculo)) {

                // si es exótico, no se puede comprar
                if (vehiculo instanceof Exotico ||
                        vehiculo.tipoVehiculo().equalsIgnoreCase("EXOTICO")) {
                    return false;
                }

                // si no tiene dinero suficiente, no compra
                if (jugador.dinero() < vehiculo.precioVehiculo()) {
                    return false;
                }

                // descuenta dinero y agrega el vehículo al jugador
                jugador.restarDinero(vehiculo.precioVehiculo());
                jugador.garaje().agregarVehiculo(vehiculo);

                // se elimina del stock
                stock.eliminar(i);

                return true;
            }
        }

        // no se encontró el vehículo
        return false;
    }

    /**
     * Devuelve el stock completo del concesionario.
     *
     * @return vector con todos los vehículos disponibles.
     */
    public Vector<Vehiculo> obtenerStock() {
        return stock;
    }
}