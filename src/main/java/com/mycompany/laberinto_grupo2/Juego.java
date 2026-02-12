/*
 * Clase principal del juego con sistema de puntaje, guardado y botón salir
 * Soporta 10 niveles
 */

package com.mycompany.laberinto_grupo2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

/**
 *
 * @author masalgue
 */
public class Juego extends JPanel {
    Laberinto laberinto = new Laberinto();
    Personaje personaje = new Personaje();
    public static int nivel = 1;
    public static int puntaje = 0;
    private String nombreJugador;
    
    // Coordenadas y dimensiones del botón Salir
    private int botonSalirX = 750;
    private int botonSalirY = 35;
    private int botonSalirAncho = 150;
    private int botonSalirAlto = 25;
    private boolean botonSalirHover = false;
    
    // Constructor para nueva partida
    public Juego(String nombre) {
        this(nombre, 1, 0);
    }
    
    // Constructor para continuar partida guardada
    public Juego(String nombre, int nivelInicial, int puntajeInicial) {
        this.nombreJugador = nombre;
        nivel = nivelInicial;
        puntaje = puntajeInicial;
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                personaje.teclaPresionada(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        // Agregar listener para detectar clicks en el botón Salir
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (dentroDelBotonSalir(e.getX(), e.getY())) {
                    confirmarSalida();
                }
            }
        });
        
        // Agregar listener para efecto hover del botón
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean dentroBoton = dentroDelBotonSalir(e.getX(), e.getY());
                if (dentroBoton != botonSalirHover) {
                    botonSalirHover = dentroBoton;
                    repaint();
                }
            }
        });
        
        setFocusable(true);
    }
    
    /**
     * Verifica si las coordenadas están dentro del botón Salir
     */
    private boolean dentroDelBotonSalir(int x, int y) {
        return x >= botonSalirX && x <= botonSalirX + botonSalirAncho &&
               y >= botonSalirY && y <= botonSalirY + botonSalirAlto;
    }
    
    /**
     * Muestra mensaje de confirmación y guarda la partida
     */
    private void confirmarSalida() {
        // Crear mensaje personalizado
        String mensaje = "¿Deseas salir del juego?\n\n" +
                        "Tu progreso será guardado automáticamente:\n" +
                        "• Jugador: " + nombreJugador + "\n" +
                        "• Nivel actual: " + nivel + "/10\n" +
                        "• Puntaje: " + puntaje + " puntos";
        
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Guardar y Salir",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            // Guardar progreso solo si no ha completado todos los niveles
            if (nivel <= 10) {
                GestorPartidas.guardarPartida(nombreJugador, nivel, puntaje);
                
                // Mostrar confirmación de guardado
                JOptionPane.showMessageDialog(
                    this,
                    "✓ Progreso guardado exitosamente\n\n" +
                    "Puedes continuar tu partida la próxima vez.",
                    "Partida Guardada",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            // Cerrar el juego
            System.exit(0);
        }
        // Si elige NO, simplemente vuelve al juego (no hace nada)
        requestFocus(); // Devolver el foco al panel para que sigan funcionando las teclas
    }
    
    @Override
    public void paint(Graphics grafico) {
        // Limpiar completamente el panel (esto elimina el rastro del personaje)
        super.paint(grafico);
        
        // Dibujar el laberinto
        laberinto.paint(grafico);
        
        // Dibujar el personaje
        personaje.paint(grafico);
        
        // Dibujar información del jugador (nombre y puntaje)
        dibujarInformacion(grafico);
        
        // Dibujar botón de salir
        dibujarBotonSalir(grafico);
    }
    
    private void dibujarInformacion(Graphics grafico) {
        // Configurar fuente
        grafico.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Dibujar puntaje en la esquina superior izquierda
        grafico.setColor(Color.WHITE);
        grafico.fillRect(5, 5, 180, 25);
        grafico.setColor(Color.BLACK);
        grafico.drawRect(5, 5, 180, 25);
        grafico.drawString("Puntaje: " + puntaje, 10, 23);
        
        // Dibujar nombre del jugador en la parte superior derecha
        int nombreWidth = grafico.getFontMetrics().stringWidth("Jugador: " + nombreJugador);
        int xPosicion = 920 - nombreWidth - 15;
        
        grafico.setColor(Color.WHITE);
        grafico.fillRect(xPosicion - 5, 5, nombreWidth + 10, 25);
        grafico.setColor(Color.BLACK);
        grafico.drawRect(xPosicion - 5, 5, nombreWidth + 10, 25);
        grafico.drawString("Jugador: " + nombreJugador, xPosicion, 23);
        
        // Dibujar nivel actual
        grafico.setColor(Color.WHITE);
        grafico.fillRect(5, 35, 120, 25);
        grafico.setColor(Color.BLACK);
        grafico.drawRect(5, 35, 120, 25);
        grafico.drawString("Nivel: " + nivel + "/10", 10, 53);
    }
    
    /**
     * Dibuja el botón de salir con efecto hover
     */
    private void dibujarBotonSalir(Graphics grafico) {
        grafico.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Color del botón cambia si el mouse está encima
        if (botonSalirHover) {
            // Efecto hover - color más oscuro
            grafico.setColor(new Color(200, 0, 0)); // Rojo oscuro
        } else {
            // Color normal
            grafico.setColor(new Color(220, 50, 50)); // Rojo
        }
        
        // Rellenar botón
        grafico.fillRect(botonSalirX, botonSalirY, botonSalirAncho, botonSalirAlto);
        
        // Borde del botón
        grafico.setColor(Color.BLACK);
        grafico.drawRect(botonSalirX, botonSalirY, botonSalirAncho, botonSalirAlto);
        
        // Texto del botón
        grafico.setColor(Color.WHITE);
        String textoBoton = "SALIR Y GUARDAR";
        int textoWidth = grafico.getFontMetrics().stringWidth(textoBoton);
        int textoX = botonSalirX + (botonSalirAncho - textoWidth) / 2;
        int textoY = botonSalirY + 17;
        grafico.drawString(textoBoton, textoX, textoY);
        
        // Efecto de sombra cuando está en hover
        if (botonSalirHover) {
            grafico.setColor(new Color(0, 0, 0, 50));
            grafico.drawRect(botonSalirX + 2, botonSalirY + 2, botonSalirAncho, botonSalirAlto);
        }
    }
    
    public static int cambiaNivel() {
        // Incrementar puntaje al pasar de nivel
        puntaje += 100 * nivel; // Más puntos por niveles más difíciles
        return nivel++;
    }

    public static int obtieneNivel() {
        return nivel;
    }
    
    public static int obtienePuntaje() {
        return puntaje;
    }
    
    public static void reiniciarJuego() {
        nivel = 1;
        puntaje = 0;
    }
    
    /*// MÉTODO MAIN
    public static void main(String[] args) {
        try {
            // Establecer look and feel del sistema
            javax.swing.UIManager.setLookAndFeel(
                javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar ventana de registro en el hilo de eventos
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegistroJugador registro = new RegistroJugador();
                registro.setVisible(true);
            }
        });
    }*/
}