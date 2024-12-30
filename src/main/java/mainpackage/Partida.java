package mainpackage;

import java.util.ArrayList;

public class Partida {
    public ArrayList<Superviviente> supervivientes;
    private int turnoActual = 0;
    
//    public Partida(ArrayList<Superviviente> supervivientes) {
//        this.supervivientes = new ArrayList<>(supervivientes);
//        this.turnoActual = 0; // Inicialmente empieza con el primer jugador (índice 0)
//    }
    
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
}