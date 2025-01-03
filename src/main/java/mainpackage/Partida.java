package mainpackage;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Partida implements Serializable{
    public static ArrayList<Superviviente> supervivientes;
    private static int turnoActual = 0;
    private static Scanner scanner = new Scanner(System.in);
    private static Tablero tablero;
    private Equipo[] inventarioActual;
    private Superviviente supervivienteActual;
    private AlmacenDeAtaques almacen;
        
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
    
    private void faseSuperviviente(int eleccion){ //eleccion viene del input de la interfaz
        supervivienteActual = this.getSupervivienteActual();
        inventarioActual = supervivienteActual.getInventario();
        supervivienteActual.setAcciones(3);
        System.out.println("Turno de " + supervivienteActual.getNombre());
        for (int i= 0; i < supervivienteActual.getAcciones(); i++){
            System.out.println("Acción " + (i+1));
            switch(eleccion){
                case 1:
                    //Interfaz dara el input para el movimiento; int casillaObjetivo = [0-8]
                    supervivienteActual.setSeleccion(Entidad.accion.MOVER);
                    // supervivienteActual.activar(casillaObjetivo);
                    continue;
                case 2:
                    //Interfaz dara el input para el arma a usar
                    supervivienteActual.setSeleccion(Entidad.accion.ATACAR);
                    int alcanceTemp = supervivienteActual.getArma(a).getAlcance();
                    //supervivienteActual.activar(int a); Este es el arma [0-1];
                    Ataque ataque = supervivienteActual.getUltimoAtaque();
                    List<Casilla> objetivo = supervivienteActual.elegirObjetivo(supervivienteActual.getArma(a));
                    if(objetivo == null){
                        int intento = 0;
                        while(intento < supervivienteActual.getCasillaActual().getContadorZombis()){
                            try {
                                supervivienteActual.getCasillaActual().getZombi(intento).reaccion(supervivienteActual.getArma(a), ataque.numExitos(almacen));
                            } catch (IllegalArgumentException e) {
                                if ("Alcance".equals(e.getMessage())) {
                                    supervivienteActual.addAcciones(); // Give back 1 action
                                    intento++; // Try the next Zombi
                                }
                            }
                        
                    }
                } else {
                    // Asociar de alguna forma las opciones disponibles objetivo con un input y la interfaz
                }
            }
        }
    }
    

    private void faseZombie(){

    }

    private void faseAparición(){

    }

    public Partida(int numJugadores){
        tablero = new Tablero();
        String[] nombres = new String[numJugadores];
        for(int i = 0; i < numJugadores; i++){
            System.out.println("Introduce el nombre del superviviente " + (i+1));
            nombres[i] = scanner.next();
            supervivientes.add(new Superviviente(nombres[i], tablero.getCasilla(0, 0)));
        }{
        // LLamamos a la InterfazPrincipal
//        SwingUtilities.invokeLater(new Runnable() {
//           @Override public void run() {
//                new InterfazPrincipal();
//            }
        }
    }
}