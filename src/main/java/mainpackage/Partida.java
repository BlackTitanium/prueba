package mainpackage;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Partida implements Serializable{
    public ArrayList<Superviviente> supervivientes;
    public ArrayList<Zombi> zombis;
    private int turnoActual = 0;
    private Scanner scanner = new Scanner(System.in);
    private Tablero tablero;
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

    public void introducirSupervivientes(String[] nombres){
        for (String nombre : nombres) {
            supervivientes.add(new Superviviente(nombre, null));
        }
    }

    private void faseSuperviviente(int eleccion){ //eleccion viene del input de la interfaz
        supervivienteActual = this.getSupervivienteActual();
        inventarioActual = supervivienteActual.getInventario();
        supervivienteActual.setAcciones(3);
        System.out.println("Turno de " + supervivienteActual.getNombre());
        while(supervivienteActual.getAcciones() > 0){
            System.out.println("Acciones restantes: " + supervivienteActual.getAcciones());
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
                    Casilla casillaObjetivo = null;
                    if(objetivo == null){
                        int intento = 0;
                        while(intento < supervivienteActual.getCasillaActual().getContadorZombis()){
                            try {
                                supervivienteActual.getCasillaActual().getZombi(intento).reaccion(supervivienteActual.getArma(a), ataque.numExitos(almacen));
                            } catch (IllegalArgumentException e) {
                                if ("Alcance".equals(e.getMessage())) { //Si es Berserker
                                    supervivienteActual.addAcciones(); // Devuelve la accion para seguir intentando
                                    intento++; // Siguiente zombi
                                }
                            }
                        
                        }
                    } else {
                     // Asociar de alguna forma las opciones disponibles objetivo con un input y la interfaz
                    // Las casillas objetivo estan en la List<Casilla> objetivo
                    // Guardar en casillaObjetivo la casilla seleccionada
                    int intento = 0;
                    while(intento < casillaObjetivo.getContadorZombis()){
                        try {
                            casillaObjetivo.getZombi(intento).reaccion(supervivienteActual.getArma(a), ataque.numExitos(almacen));
                        } catch (IllegalArgumentException e) {
                            if ("Alcance".equals(e.getMessage())) { //Si es Berserker
                                supervivienteActual.addAcciones(); // Devuelve la accion para seguir intentando
                                intento++; // Siguiente zombi
                            }
                        }
                    }
                } continue;  

                case 3: //Buscar
                    supervivienteActual.setSeleccion(Entidad.accion.BUSCAR);
                    int slotInventario = 0;
                    // Input de interfaz para elegir el slot del inventario
                    supervivienteActual.activar(slotInventario);
                    continue;
                case 4: //Elegir arma o usar provision
                    int objetoInventario = 0;
                    int ranuraObjetivo = 0;
                    // Input de interfaz para elegir el objeto del inventario, si es un arma tambien la ranura
                    supervivienteActual.elegirArma(objetoInventario, ranuraObjetivo);
                    supervivienteActual.setAcciones(supervivienteActual.getAcciones()+1);
                    continue;
                case 5: //Nada
                    break;
            }
        }
    }
    

    public void faseZombie(){
        for (int i = 0; i < zombis.size(); i++){
            zombis.get(i).activar();
        }
    }

    public void faseAparición(){
        for (int i =0; i<3; i++){
            tablero.aparicionZombi();
        }
    }

    public Partida(int numJugadores){
        tablero = new Tablero();
        String[] nombres = new String[numJugadores];
        for(int i = 0; i < numJugadores; i++){
            System.out.println("Introduce el nombre del superviviente " + (i+1));
            nombres[i] = scanner.next();
            supervivientes.add(new Superviviente(nombres[i], tablero.getCasilla(0, 0)));
        }
        // LLamamos a la InterfazPrincipal
//        SwingUtilities.invokeLater(new Runnable() {
//           @Override public void run() {
//                new InterfazPrincipal();
//            }
    }
}