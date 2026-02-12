/*
 * Clase para gestionar el guardado y carga de partidas
 */
package com.mycompany.laberinto_grupo2;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author masalgue
 */
public class GestorPartidas {
    private static final String ARCHIVO_PARTIDA = "partida_guardada.txt";
    
    /**
     * Guarda la partida actual en un archivo de texto
     */
    public static void guardarPartida(String nombreJugador, int nivelActual, int puntajeActual) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_PARTIDA))) {
            // Formato: NOMBRE|NIVEL|PUNTAJE|FECHA
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaGuardado = sdf.format(new Date());
            
            writer.write("JUGADOR:" + nombreJugador);
            writer.newLine();
            writer.write("NIVEL:" + nivelActual);
            writer.newLine();
            writer.write("PUNTAJE:" + puntajeActual);
            writer.newLine();
            writer.write("FECHA:" + fechaGuardado);
            writer.newLine();
            
            System.out.println("Partida guardada exitosamente");
        } catch (IOException e) {
            System.err.println("Error al guardar la partida: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si existe un archivo de partida guardada
     */
    public static boolean existePartidaGuardada() {
        File archivo = new File(ARCHIVO_PARTIDA);
        return archivo.exists() && archivo.length() > 0;
    }
    
    /**
     * Carga los datos de la partida guardada
     * Retorna un array con: [nombreJugador, nivel, puntaje, fecha]
     */
    public static String[] cargarPartida() {
        String[] datos = new String[4];
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_PARTIDA))) {
            String linea;
            int indice = 0;
            
            while ((linea = reader.readLine()) != null && indice < 4) {
                if (linea.startsWith("JUGADOR:")) {
                    datos[0] = linea.substring(8); // Nombre del jugador
                } else if (linea.startsWith("NIVEL:")) {
                    datos[1] = linea.substring(6); // Nivel
                } else if (linea.startsWith("PUNTAJE:")) {
                    datos[2] = linea.substring(8); // Puntaje
                } else if (linea.startsWith("FECHA:")) {
                    datos[3] = linea.substring(6); // Fecha
                }
                indice++;
            }
            
            System.out.println("Partida cargada exitosamente");
            return datos;
        } catch (IOException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Elimina el archivo de partida guardada (para nueva partida)
     */
    public static void eliminarPartidaGuardada() {
        File archivo = new File(ARCHIVO_PARTIDA);
        if (archivo.exists()) {
            if (archivo.delete()) {
                System.out.println("Partida anterior eliminada");
            } else {
                System.err.println("No se pudo eliminar la partida anterior");
            }
        }
    }
    
    /**
     * Obtiene solo el nombre del jugador guardado
     */
    public static String obtenerNombreJugadorGuardado() {
        String[] datos = cargarPartida();
        if (datos != null && datos[0] != null) {
            return datos[0];
        }
        return null;
    }
    
    /**
     * Obtiene información resumida de la partida guardada
     */
    public static String obtenerInfoPartidaGuardada() {
        String[] datos = cargarPartida();
        if (datos != null) {
            return "Jugador: " + datos[0] + "\n" +
                   "Nivel: " + datos[1] + "\n" +
                   "Puntaje: " + datos[2] + "\n" +
                   "Guardado: " + datos[3];
        }
        return "No hay información disponible";
    }
}