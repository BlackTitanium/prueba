package mainpackage;

import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Partida {
    public static ArrayList<Superviviente> supervivientes;
    private static int turnoActual = 0;
    private static Scanner scanner = new Scanner(System.in);
    private static Tablero tablero;
        
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

    public static void introducirSupervivientes(String[] nombres){
        for (String nombre : nombres) {
            supervivientes.add(new Superviviente(nombre, null));
        }
    }
    
    private void faseSuperviviente(int eleccion){
        switch(eleccion){
            case(1): // Moverse

                break;
            case(2): // Buscar
                break;
            case(3): // Atacar
                break;
            case(4): // Elegir arma
                break;
            case(5): // No hacer nada
                break;
        }
    }

    private void faseZombie(){

    }

    private void faseAparición(){

    }

    public static void main(String args[]){
//        tablero = new Tablero();
//        Casilla casillaInicio = tablero.getCasilla(0, 0);
//
//        System.out.print("Elige el número de jugadores (1, 2, 3 o 4): ");
//        int num = scanner.nextInt();
//
//        supervivientes = new ArrayList<>();
//        String nombre;
//        for(int i = 0; i<num; i++){
//            System.out.print("Dime el nombre del jugador " + (i+1) + ": ");
//            nombre = scanner.nextLine();
//            supervivientes.add(new Superviviente(nombre,casillaInicio));
//        }
        // LLamamos a la InterfazPrincipal
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new InterfazPrincipal();
            }
        });
    }
}