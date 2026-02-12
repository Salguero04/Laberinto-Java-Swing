/*
 * Clase Personaje - Representa al jugador en el laberinto
 */
package com.mycompany.laberinto_grupo2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 *
 * @author masalgue
 */
public class Personaje {
    Laberinto lab = new Laberinto();
    int x = 40;
    int y = 40;
    int ancho = 40;
    int alto = 40;
    int movimiento = 40;
    
    public void paint(Graphics grafico) {
        // Dibujar el personaje (cara sonriente)
        grafico.setColor(Color.black);
        grafico.fillOval(x, y, ancho, alto);
        
        // Ojos
        grafico.setColor(Color.white);
        grafico.fillOval(x + 7, y + 8, 13, 13);
        grafico.fillOval(x + 21, y + 8, 13, 13);
        grafico.setColor(Color.black);
        grafico.fillOval(x + 11, y + 12, 5, 5);
        grafico.fillOval(x + 25, y + 12, 5, 5);
       
        // Boca
        grafico.setColor(Color.black);
        grafico.fillOval(x + 7, y + 24, 27, 7);
    }
    
    public void teclaPresionada(KeyEvent evento) {
        int[][] laberinto = lab.obtieneLaberinto();
        
        if (evento.getKeyCode() == 37) { // Izquierda
            if (laberinto[y / 40][(x / 40 - 1)] != 1) {
                x = x - movimiento;
            }
        }
        if (evento.getKeyCode() == 39) { // Derecha  
            if (laberinto[y / 40][(x / 40) + 1] != 1) {
                x = x + movimiento;
            }
        }
        if (evento.getKeyCode() == 40) { // Abajo
            if (laberinto[(y / 40) + 1][x / 40] != 1) {
                y = y + movimiento;
            }
        }
        if (evento.getKeyCode() == 38) { // Arriba
            if (laberinto[(y / 40) - 1][x / 40] != 1) {
                y = y - movimiento;
            }
        }
        
        // Si llega al final
        if (x == 840 && y == 440) {
            Juego.cambiaNivel();
            x = 40;
            y = 40;
        }
    }
}