package mainpackage;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

public class Partida implements Serializable{
    public static ArrayList<Superviviente> supervivientes;
    private static int turnoActual = 0;
    private static Scanner scanner = new Scanner(System.in);
    private static Tablero tablero;
    private Equipo[] inventarioActual;
    private Superviviente supervivienteActual;
    private AlmacenDeAtaques almacen;
        
    public static Superviviente getSupervivienteActual() {
        return supervivientes.get(turnoActual);
    }
    
    public Superviviente getSupervivienteIndice(int indice) {
        if (indice >= 0 && indice < supervivientes.size()) {
            return supervivientes.get(indice);
        }
        return null;
    }
    
    public static int getTurnoActual(){
        return turnoActual;
    }
    
    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % supervivientes.size();
    }

    public static void colocarElementosIniciales(){
        supervivientes = new ArrayList<>();
        Casilla.inicializarArrayList();
        InterfazPrincipal.reiniciarTablero();
        StringBuilder sb1 = new StringBuilder();
        sb1.append("<html>"); // Inicio con HTML
        for(int i = 0; i<PanelInicio.nJugadores; i++){
            Superviviente s = new Superviviente(PanelInicio.nombres[i], InterfazPrincipal.casillas[0][0]);
            InterfazPrincipal.casillas[0][0].addSuperviviente(s);
            Partida.supervivientes.add(s);
            sb1.append(s.getNombre());
            InterfazPrincipal.botones[0][0].setText(sb1.toString());
            sb1.append("<br>"); // Salto de linea en HTML
        }
        sb1.append("</html>"); // Final con HTML
        Random random = new Random();
        InterfazPrincipal.posicionesOcupadas[0][0] = true; // Marcar la [0][0] como ocupada
        for (int i = 0; i < 3; i++) {
            int x, y;
            do { 
                x = random.nextInt(10); 
                y = random.nextInt(10); 
            } while (InterfazPrincipal.posicionesOcupadas[x][y]);
            
            InterfazPrincipal.posicionesOcupadas[x][y] = true; // Marcar la nueva casilla como ocupada
//            
//            Zombi z = new Zombi(tablero,InterfazPrincipal.casillas[x][y], );  // Crear un zombi
//            InterfazPrincipal.casillas[x][y].addZombi(z);  // Añadir zombi a la casilla
            StringBuilder sb2 = new StringBuilder();
            sb2.append("<html>");
            sb2.append("Z"/*Zombie tipo*/); // Cambiar por el método que devuelve el String Zombie Tipo
            sb2.append("<br>");
            sb2.append("</html>");
            InterfazPrincipal.botones[x][y].setText(sb2.toString());  // Mostrar "Z" + tipo en el botón
        }
        InterfazPrincipal.cardLayout.show(InterfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
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
    
    // Añadir zombie
    public void addZombie(){
        Random random = new Random();
        int x, y;
        do { 
            x = random.nextInt(10); 
            y = random.nextInt(10); 
        } while (InterfazPrincipal.posicionesOcupadas[x][y]);

        InterfazPrincipal.posicionesOcupadas[x][y] = true; // Marcar la nueva casilla como ocupada
        
        Zombi z = new Zombi(tablero,InterfazPrincipal.casillas[x][y], );  // Crear un zombi
        InterfazPrincipal.casillas[x][y].addZombi(z);  // Añadir zombi a la casilla
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(/*Zombie tipo*/);
        sb.append("<br>");
        sb.append("</html>");
        InterfazPrincipal.botones[x][y].setText(sb.toString());  // Mostrar "Z" + tipo en el botón
    }

    private void faseZombie(){

    }

    private void faseAparición(){

    }

    public Partida(/*int numJugadores*/){
        tablero = new Tablero();
        // LLamamos a la InterfazPrincipal
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                new InterfazPrincipal();
            }
        });

//        String[] nombres = new String[numJugadores];
//        for(int i = 0; i < numJugadores; i++){
//            System.out.println("Introduce el nombre del superviviente " + (i+1));
//            nombres[i] = scanner.next();
//            supervivientes.add(new Superviviente(nombres[i], tablero.getCasilla(0, 0)));
//        }
    }
}