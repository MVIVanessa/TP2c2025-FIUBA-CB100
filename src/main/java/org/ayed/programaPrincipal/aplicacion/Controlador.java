package org.ayed.programaPrincipal.aplicacion;

import org.ayed.gta.Concesionario.Concesionario;
import org.ayed.gta.Garaje.Garaje;
import org.ayed.gta.Misiones.Mision;
import org.ayed.gta.Partida;
import org.ayed.gta.Vehiculos.Vehiculo;
import org.ayed.programaPrincipal.frontend.Interfaz;
import org.ayed.programaPrincipal.frontend.formulario.Campo;
import org.ayed.programaPrincipal.frontend.formulario.TipoCampo;
import org.ayed.programaPrincipal.menu.MenuConcesionario;
import org.ayed.programaPrincipal.menu.MenuGaraje;
import org.ayed.programaPrincipal.menu.MenuPartida;
import org.ayed.tda.vector.Vector;

public class Controlador {
    private final Partida partida;
    private final Garaje garaje;
    private final Concesionario concesionario;
    
    private final MenuPartida menuPartida;
    private final MenuGaraje menuGaraje;
    private final MenuConcesionario menuConcesionario;
    
    private int[] datosJugador; // [dia actual, dinero, creditos]

    /**
     * Coordina la lógica de la partida y la interfaz gráfica.
     * Recibe eventos de la UI, ejecuta acciones del modelo
     * y decide qué pantalla mostrar a continuación.
     */
    public Controlador() {
        this.garaje = new Garaje();
        this.concesionario = new Concesionario();
        this.partida = new Partida(garaje, this);
        menuPartida = new MenuPartida(partida);
        menuGaraje = new MenuGaraje(partida.nombre(), partida.garaje(), this);
        menuConcesionario = new MenuConcesionario(partida, concesionario, this);
    }

    public void iniciar() {
        mostrarMensaje(
            "¡Bienvenido/a a GTA VI: Conduciendo Por La Ciudad!",
            () -> mostrarIngresoNombreJugador(new Campo[] {
                new Campo("Nombre", TipoCampo.TEXTO)
            })
        );
    }
    
    public void empezarMision() {
        if (!partida.puedeJugar()) {
            mostrarMensaje(
                "Game Over\nNo se puede continuar la partida. No tiene suficiente dinero.",
                () -> Interfaz.cerrar()
            );
            return;
        }
        mostrarMenuDificultad();
    }


   // ----------------------- MÉTODOS PARA MOSTRAR MENÚS Y MENSAJES A TRAVÉS DE LA INTERFAZ GRÁFICA -------------------------
   // Lógica ->  Controlador (preparar datos) -> Interfaz (mostrar menú con datos)

   // MENÚS

    public void mostrarMenuPrincipal() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuPrincipal(partida.nombre(), datosJugador);
    }

    public void mostrarMenuGaraje() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuGaraje(partida.nombre(), datosJugador);
    }

    public void mostrarMenuDificultad() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuDificultad(partida.nombre(), datosJugador);
    }

    public void mostrarMenuContinuarJugando() {
        if(!partida.noFallo())
            mostrarMenuPrincipal();
        else{
            datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
            Interfaz.getInstancia().mostrarMenuContinuarJugando(partida.nombre(), datosJugador);
        }
    }

    public void mostrarMenuConcesionario() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarMenuConcesionario(partida.nombre(), datosJugador);
    }

    public void mostrarMenuCompraConcesionario() {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        String [] vehiculos = vectorToStringCadena(concesionario.obtenerStock());
        Interfaz.getInstancia().mostrarMenuCompraConcesionario(partida.nombre(), datosJugador, vehiculos);
    }

    // INGRESO DE DATOS

    public void mostrarIngresoNombreJugador(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
             this::procesarNombreJugador
        );
    }

    public void mostrarFormularioCreditosAgregados(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarCreditosAgregados
        );
    }

    public void mostrarFormularioEliminar(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarEliminacion
        );
    }

    // OTROS

    public void mostrarMensaje(String msg, Runnable onContinuar) {
        Interfaz.getInstancia().mostrarMensaje(msg, onContinuar);
    }

    public void mostrarVehiculosPermitidos(Mision mision) {
        datosJugador = new int[] {partida.diaActual(), partida.dinero(), partida.garaje().obtenerCreditos()};
        Interfaz.getInstancia().mostrarVehiculosPermitidos(mision, partida.nombre(), datosJugador);
    }

    public void iniciarMision(Mision mision) {
        Interfaz.getInstancia().iniciarMision(mision);
    }

    public void mostrarCargaPorIndice(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarCargaPorIndice
        );
    }

	public void mostrarVehiculos(Runnable callback, Vector<Vehiculo> vehiculos) {
		String autos = vectorToString(vehiculos);
		if (autos.isEmpty()) {
			mostrarMensaje("No hay vehículos almacenados.", () -> { callback.run(); });
		} else {
			mostrarMensaje("Vehículos en el garaje:\n" +
                            "Nombre \t|\tMarca \t|\tPrecio \t|\tTipo \t|\tCant. Ruedas \t|\tCapacidad de Gasolina \t|\tVelocidad Máxima\n" +
                            autos,
				() -> callback.run());	
		}
	}

    public void mostrarBusquedaPorNombre(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarBusquedaPorNombre
         );
    }

    public void mostrarBusquedaPorMarca(Campo[] campos) {
        Interfaz.getInstancia().mostrarFormulario(
            campos,
            this::procesarBusquedaPorMarca
         );
    }

    // ----------------------- MÉTODOS PARA PROCESAR LAS OPCIONES SELECCIONADAS EN LOS MENÚS -------------------------
    // Interfaz (muestra menú; obtiene opción) -> Controlador (procesar opción) -> Lógica (ejecutar acción según opción)

    // MENÚS
    public void procesarMenuPrincipal(int opcion) {
        menuPartida.procesarOpcion(opcion, this);
    }

    public void procesarMenuGaraje(int opcion) {
        menuGaraje.procesarOpcion(opcion, this);
    }

    public void procesarMenuDificultad(int opcion) {
        partida.elegirModo(opcion);

        Mision m = partida.misionActual();
        int permitidos= m.vehiculosPermitidos(garaje);
        if (permitidos==0) {
            mostrarMensaje(
            "No tienes vehículos permitidos para esta misión.\nElige otra dificultad.",
            this::mostrarMenuDificultad
        );
            return;
        }
        mostrarVehiculosPermitidos(m);
    }

    public void procesarMenuConcesionario(int opcion) {
        menuConcesionario.procesarOpcion(opcion, this);
    }

    public void procesarMenuContinuarJugando(int opcion) {
        if (opcion == 1) {
            mostrarMenuDificultad();
        } else {
            mostrarMenuPrincipal();
        }
    }

    // OTROS

    public void procesarSeleccionVehiculo(int opcion) {
        Mision m = partida.misionActual();
        m.seleccionarVehiculo(opcion);

        iniciarMision(m);
    }
    private void procesarCreditosAgregados(String[] datos) {
        menuGaraje.agregarCreditos(datos);
        mostrarMensaje(
        "Créditos actuales: " + garaje.obtenerCreditos(), () -> mostrarMenuGaraje());
    }

    private void procesarCargaPorIndice(String[] datos) {
        int indice = Integer.parseInt(datos[0]);
        int litros = Integer.parseInt(datos[1]);
        menuGaraje.cargarVehiculo(new int[]{indice, litros});
        mostrarMensaje(
            "Vehículo cargado correctamente.", () -> mostrarMenuGaraje()
        );
    }

    private void procesarNombreJugador(String[] datos) {
        partida.guardarNombre(datos[0]);
        mostrarMensaje("Bienvenido/a " + datos[0] + "!", () -> mostrarMenuPrincipal());
    }

    public void procesarMisionFinalizada(boolean completada) {
        partida.finalizarMision(completada);

        if (completada) {
            mostrarMensaje(
                "¡Misión completada!",
                this::mostrarMenuContinuarJugando
            );
        } else {
            mostrarMensaje(
                "Misión fallida",
                this::mostrarMenuContinuarJugando
            );
        }
    }
    /**
     * Procesa la eliminacion del un vehiculo del garaje
     * @param datos Indice del vehiculo a eliminar
     */
    public void procesarEliminacion(String[] datos) {
        boolean eliminado = menuGaraje.eliminar(datos[0]);
        if (eliminado) {
            mostrarMensaje("Vehículo eliminado correctamente.", this::mostrarMenuGaraje);
        } else {
            mostrarMensaje("No se encontró ningún vehículo con ese nombre.", this::mostrarMenuGaraje);
        }

    }
        public void procesarBusquedaPorNombre(String[] datos) {
            String nombre = datos[0];

            Vector<Vehiculo> resultado =
                concesionario.buscarPorNombre(nombre);

            if (resultado.vacio()) {
                mostrarMensaje(
                    "No se encontraron vehículos con ese nombre.",
                    this::mostrarMenuConcesionario
                );
            } else {
                mostrarVehiculos(
                    this::mostrarMenuConcesionario,
                    resultado
                );
            }
        }

        public void procesarBusquedaPorMarca(String[] datos) {
            String marca = datos[0];

            Vector<Vehiculo> resultado =
                concesionario.buscarPorMarca(marca);

            if (resultado.vacio()) {
                mostrarMensaje(
                    "No se encontraron vehículos de esa marca.",
                    this::mostrarMenuConcesionario
                );
            } else {
                mostrarVehiculos(
                    this::mostrarMenuConcesionario,
                    resultado
                );
            }
        }

        public void procesarCompraVehiculo(int indiceVehiculo) {
            
            String nombreVehiculo = concesionario.obtenerStock().dato(indiceVehiculo-1).nombreVehiculo();
            menuConcesionario.comprar(nombreVehiculo);

            if (menuConcesionario.operacionExitosa()) {
                mostrarMensaje(
                    "¡Compra exitosa! " + nombreVehiculo + " ha sido agregado a tu garaje.",
                    this::mostrarMenuConcesionario
                );
            } else {
                mostrarMensaje(
                    "No se pudo completar la compra. Asegúrate de tener suficiente dinero",
                    this::mostrarMenuConcesionario
                );
            }
        }


    // ----------------------- MÉTODOS AUXILIARES -------------------------

    private String vectorToString(Vector<Vehiculo> vehiculos) {
        String resultado = "";
        for (int i = 0; i < vehiculos.tamanio(); i++) {
            resultado += vehiculos.dato(i).informacionVehiculoUI() + "\n";
        }
        return resultado;
    }

    private String[] vectorToStringCadena(Vector<Vehiculo> vehiculos) {
        String[] resultado = new String[vehiculos.tamanio()];
        for (int i = 0; i < vehiculos.tamanio(); i++) {
            resultado[i] = vehiculos.dato(i).informacionVehiculoUI();
        }
        return resultado;
    }

        // ----------------------- GETTERS -------------------------

    public Garaje garaje() {
        return garaje;
    }

    public Partida partida() {
        return partida;
    }

    public void cerrar() {
        Interfaz.cerrar();
    }

}

