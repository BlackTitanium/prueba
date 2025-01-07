package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Partida implements Serializable{
    public ArrayList<Superviviente> supervivientes;
    public ArrayList<Zombi> zombis;
    private int turnoActual = 0;
//    private Scanner scanner = new Scanner(System.in);
    private Tablero tablero;
    private Equipo[] inventarioActual;
    private Superviviente supervivienteActual;
    private AlmacenDeAtaques almacen;
    private InterfazPrincipal interfazPrincipal;
    private static int idZombiCont = 1;

    public InterfazPrincipal getInterfazPrincipal(){
        return interfazPrincipal;
    }
    
    public void setTurnoActual(int turno){
        turnoActual = turno;

    }
    public Superviviente getSupervivienteActual() {
        if (supervivientes == null || supervivientes.isEmpty()) {
            throw new IllegalStateException("No hay supervivientes en la lista.");
        }
        return supervivientes.get(turnoActual);
    }
    
    public Superviviente getSupervivienteIndice(int indice) {
        if (indice >= 0 && indice < supervivientes.size()) {
            return supervivientes.get(indice);
        }
        return null;
    }
    
    public ArrayList<Superviviente> getSupervivientes() {
        return supervivientes;
    }
    
    public Tablero getTablero() {
        return tablero;
    }

    public AlmacenDeAtaques getAlmacenDeAtaques(){
        return almacen;
    }
    public int getTurnoActual(){
        return turnoActual;
    }
    
    public void avanzarTurno() {
        turnoActual = (turnoActual + 1) % supervivientes.size();
    }

    public void introducirSupervivientes(String[] nombres){
        for (String nombre : nombres) {
            supervivientes.add(new Superviviente(nombre, null, tablero, this));
        }
    }

    public Equipo crearEquipo(){
        Random random = new Random();
        int n = random.nextInt(2);
        Equipo equipo = null;
        if(n==0){
            equipo = new Arma();
        }
        if(n==1){
            equipo = new Provision();
        }
        return equipo;
    }

    public void colocarElementosIniciales(String[] nombres){
        supervivientes = new ArrayList<Superviviente>(interfazPrincipal.nJugadores);
        Casilla casillaInicial = new Casilla(0,0);
        interfazPrincipal.inicializarTablero();
        
        StringBuilder sb1 = new StringBuilder();
        sb1.append("<html>"); // Inicio con HTML
        for(int i = 0; i<interfazPrincipal.nJugadores; i++){
            Superviviente s = new Superviviente(nombres[i], casillaInicial, tablero, this);
            casillaInicial.addEntidad(s);
            supervivientes.add(s);
            sb1.append(s.getNombre());
            sb1.append("<br>"); // Salto de linea en HTML
        }
        tablero.setMapa(casillaInicial);
        sb1.append("</html>"); // Final con HTML
        String textoBotonSupervivientes = sb1.toString();

        supervivienteActual = supervivientes.get(0);

        interfazPrincipal.botones[0][0].setText(textoBotonSupervivientes);
        tablero.posicionesOcupadas[0][0] = true; // Marcar la [0][0] como ocupada
        
        // Crear los 3 zombis
        for(int i=0;i<3;i++){
            faseApariciónZombi();
        }
        
        // Cambiar el panel derecho
        interfazPrincipal.inicializarPaneles();
        interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
        interfazPrincipal.gestorTurnos();
    }

    public void activarSuperviviente(int ranura, int x, int y){
        switch(supervivienteActual.getSeleccion()){
            case Entidad.accion.MOVER:
                //Interfaz dara el input para el movimiento; int casillaObjetivo = [0-8]
                supervivienteActual.activar(ranura, x, y);
                break;
            case Entidad.accion.ATACAR: //Atacar
                    int alcanceTemp = supervivienteActual.getArmas()[ranura].getAlcance();
                    supervivienteActual.activar(ranura,0,0); 
                    Ataque ataque = supervivienteActual.getUltimoAtaque();
                    ArrayList<Casilla> objetivo = supervivienteActual.elegirObjetivo(supervivienteActual.getArmas()[ranura]);
                    Casilla casillaObjetivo = tablero.getCasilla(x, y);
                    if(objetivo == null){
                        int intento = 0;
                        while(intento < supervivienteActual.getCasillaActual().getContadorZombis()){
                            try {
                                supervivienteActual.getCasillaActual().getZombi(intento).reaccion(supervivienteActual.getArmas()[ranura], ataque.numExitos(almacen));
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
                            casillaObjetivo.getZombi(intento).reaccion(supervivienteActual.getArmas()[ranura], ataque.numExitos(almacen));
                        } catch (IllegalArgumentException e) {
                            if ("Alcance".equals(e.getMessage())) { //Si es Berserker
                                supervivienteActual.addAcciones(); // Devuelve la accion para seguir intentando
                                intento++; // Siguiente zombi
                            }
                        }
                    }
                }  

                case Entidad.accion.BUSCAR: //Buscar
                    // Input de interfaz para elegir el slot del inventario
                    supervivienteActual.activar(ranura,0,0);
                case Entidad.accion.INVENTARIO: //Elegir arma o usar provision
                    int objetoInventario = ranura;
                    int ranuraObjetivo = x;
                    // Input de interfaz para elegir el objeto del inventario, si es un arma tambien la ranura
                    supervivienteActual.elegirArma(objetoInventario, ranuraObjetivo);
                    supervivienteActual.setAcciones(supervivienteActual.getAcciones()+1);
                case Entidad.accion.NADA: //Nada
                    break;
        }
        // NS SI PONERLO AQUÍ O EN EL GESTOR DE TURNOS AL FINAL, ES PARA QUE SE ACTUALIZEN LAS ACCIONES EN LA INTERFAZ
        //interfazPrincipal.panelMenuJugador.actualizarLabels();
    }
    public void faseSuperviviente(){ //eleccion viene del input de la interfaz
        supervivienteActual = this.getSupervivienteActual();
        inventarioActual = supervivienteActual.getInventario();
        supervivienteActual.setAcciones(3);
        System.out.println("Acciones restantes: " + supervivienteActual.getAcciones());
    }

    public void faseZombie(){
        for (int i = 0; i < zombis.size(); i++){
            zombis.get(i).activar();
        }
    }

    public void faseApariciónZombi(){
        // Generar casilla random no ocupada
        Random random = new Random();
        int x, y;
        do { 
            x = random.nextInt(10); 
            y = random.nextInt(10); 
        } while (tablero.posicionesOcupadas[x][y]);        
        tablero.posicionesOcupadas[x][y] = true; // Marcar la nueva casilla como ocupada
        // Crear Zombi
        Zombi z =  null;
        int subtipo = random.nextInt(3);
        switch(subtipo){
            case 0:
                z = new Zombi(tablero.getCasilla(x, y),"NORMAL",this,idZombiCont);
                break;
            case 1:
                z = new Toxico(tablero.getCasilla(x, y),"TOXICO",this,idZombiCont);
                break;
            case 2:
                z = new Berserker(tablero.getCasilla(x, y),"BERSERKER",this,idZombiCont);
                break;
        }
        // Añadir al tablero
        tablero.getCasilla(x, y).addEntidad(z);
        // Mostrar el Zombi y su tipo en el Tablero(interfaz)
        interfazPrincipal.botones[x][y].setText(z.getZombiParaBoton());
        // Incrementar el contador de zombis
        idZombiCont++;
    }

    public Partida(){
        tablero = new Tablero(this);
        almacen =  new AlmacenDeAtaques();

        // LLamamos a la InterfazPrincipal
        interfazPrincipal = new InterfazPrincipal(this);
    }
}