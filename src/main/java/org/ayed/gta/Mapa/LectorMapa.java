package org.ayed.gta.Mapa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.ayed.tda.lista.Lista;

public class LectorMapa {
    
    public Lista<Lista<TipoCelda>> leerMapaDesdeCSV(String rutaArchivo) throws IOException { //devuelve una matriz de TipoCelda
        
        Lista<Lista<TipoCelda>> mapa = new Lista<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) { //Leemos el archivo por línea
                
                String[] celdas = linea.split(",");
                Lista<TipoCelda> fila = new Lista<>();
                
                for (String celdaActual : celdas) {
                    String celdaStr = celdaActual.trim().toUpperCase();
                    if (celdaStr.equals("C")){
                        TipoCelda celda = TipoCelda.TRANSITABLE;
                        fila.agregar(celda);
                    }
                    else if(celdaStr.equals("E")){
                        TipoCelda celda = TipoCelda.EDIFICIO;
                        fila.agregar(celda);
                    }
                }
                mapa.agregar(fila);
            }
        }
        return mapa;
    }
}