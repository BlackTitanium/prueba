package mainpackage;

import java.util.ArrayList;
import javax.swing.*;

import mainpackage.Interfaz.*;
import static mainpackage.Interfaz.InterfazPrincipal.numJ;

public class Partida {
    public ArrayList<Superviviente> supervivientes;
    private int turnoActual = 0;
    
    public void addSuperviviente(Superviviente s){
        supervivientes.add(s);
    }
    
    public Superviviente getSupervivienteActual() {
        return supervivientes.get(turnoActual);
    }
    
    public Superviviente getSupervivienteIndice(int indice) {
        if (indice >= 0 && indice < supervivientes.size()) {
            return supervivientes.get(indice);
        }
        return null;
    }
    
    public int getTurnoActual(){
        return turnoActual;
    }
    
    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % supervivientes.size();
    }
    
    private int jugadoresAñadidos = 0;
    
    public void añadirSuperviviente(JFrame parent){
        if(jugadoresAñadidos < InterfazInicio.nJugadores){
            numJ = jugadoresAñadidos+1;
//            InterfazNombreJugador dialog = new InterfazNombreJugador();
//            dialog.setModal(true); // Bloquea el bucle mientras InterfazNombreJugador este abierto
//            dialog.setVisible(true);
            InterfazNombreJugador dialog = new InterfazNombreJugador(parent);
            dialog.mostrar();

            if(!InterfazNombreJugador.nombre.isEmpty()){
                Superviviente s = new Superviviente(InterfazNombreJugador.nombre);
                InterfazPrincipal.casillas[0][0].addSuperviviente(s);
                InterfazPrincipal.botones[0][0].setText("S" + (jugadoresAñadidos+1));  // Mostrar "S" + (jugadoresAñadidos+1) en el botón
                addSuperviviente(s);
                
                jugadoresAñadidos++;
            } else {
                JOptionPane.showMessageDialog(null,"Debes ingresar un nombreeeee.");
            }
            añadirSuperviviente(parent);
        }
    }
    
    public void main(String args[]){
        new InterfazInicio().setVisible(true);
    }
}