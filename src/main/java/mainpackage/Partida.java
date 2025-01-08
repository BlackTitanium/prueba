package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

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
    public boolean victoria = false;
    private final Object monitor = new Object();

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
        int turnoSuperviviente = getTurnoSupervivienteActual();
        return supervivientes.get(turnoSuperviviente);
    }

    public int getTurnoSupervivienteActual(){
        return turnoActual % supervivientes.size();
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
        turnoActual++;
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
        zombis = new ArrayList<>();
        Casilla casillaInicial = new Casilla(0,0);
        
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
        interfazPrincipal.activarActionListener();
        interfazPrincipal.inicializarPaneles();
        interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
        SwingUtilities.invokeLater(() -> {
            new Thread(() -> {gestorTurnos();}).start();
        });
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
                    Casilla casillaActual = supervivienteActual.getCasillaActual();
                    int nExitos = ataque.getNumExitos();
                    if(objetivo == null){
                        int intento = 0;
                        while(intento < casillaActual.getContadorZombis() || nExitos > 0){
                            try {
                                int vivoOmuerto = casillaActual.getZombi(intento).reaccion(supervivienteActual.getArmas()[ranura]);
                                if(vivoOmuerto == 1 || vivoOmuerto == 2){ // EL zombi a muerto (Si es 2 es porque era toxico y ademas estaba en la casilla con un sueprviviente)
                                    supervivienteActual.añadirZombiAsesinado(casillaActual.getZombi(intento).infoZombi());
                                    casillaActual.removeEntidad(casillaActual.getZombi(intento));
                                    zombis.remove(casillaActual.getZombi(intento));
                                    interfazPrincipal.botones[x][y].setText("<html></html>");
                                    nExitos--;
                                }
                            } catch (IllegalArgumentException e) {
                                if ("Alcance".equals(e.getMessage())) { //Si es Berserker
                                    supervivienteActual.addAcciones(); // Devuelve la accion para seguir intentando
                                }
                            }
                            intento++; // Siguiente zombi
                        }
                    } else {
                     // Asociar de alguna forma las opciones disponibles objetivo con un input y la interfaz
                    // Las casillas objetivo estan en la List<Casilla> objetivo
                    // Guardar en casillaObjetivo la casilla seleccionada
                    int intento = 0;
                    while(intento < casillaObjetivo.getContadorZombis() || nExitos > 0){
                        try {
                            int vivoOmuerto = casillaActual.getZombi(intento).reaccion(supervivienteActual.getArmas()[ranura]);
                            if(vivoOmuerto == 1 || vivoOmuerto == 2){ // EL zombi a muerto (Si es 2 ademas era toxico)
                                supervivienteActual.añadirZombiAsesinado(casillaActual.getZombi(intento).infoZombi());
                                casillaActual.removeEntidad(casillaActual.getZombi(intento));
                                zombis.remove(casillaActual.getZombi(intento));
                                interfazPrincipal.botones[x][y].setText("<html></html>");
                                nExitos--;
                            }
                        } catch (IllegalArgumentException e) {
                            if ("Alcance".equals(e.getMessage())) { //Si es Berserker
                                supervivienteActual.addAcciones(); // Devuelve la accion para seguir intentando
                            }
                        }
                        intento++; // Siguiente zombi
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
        accionRealizada();
        // NS SI PONERLO AQUÍ O EN EL GESTOR DE TURNOS AL FINAL, ES PARA QUE SE ACTUALIZEN LAS ACCIONES EN LA INTERFAZ
        //interfazPrincipal.panelMenuJugador.actualizarLabels();
    }
    public void faseSuperviviente(){ //eleccion viene del input de la interfaz
        supervivienteActual = this.getSupervivienteActual();
        inventarioActual = supervivienteActual.getInventario();
        supervivienteActual.setAcciones(3);
        System.out.println("Acciones restantes: " + supervivienteActual.getAcciones() + " de " + supervivienteActual.getNombre());
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
        // Añadir al arrayList
        zombis.add(z);
        // Incrementar el contador de zombis
        idZombiCont++;
    }

    public void gestorTurnos(){
        setTurnoActual(0);
        //for (int i = 0; i < getSupervivientes().size(); i++) {
        while(!victoria){
            faseSuperviviente();
            Superviviente supervivienteActual = getSupervivienteActual();
//            SwingUtilities.invokeLater(() -> {
//                // Actualizar para el siguiente jugador
//                interfazPrincipal.panelMenuJugador.actualizarLabels();
//                interfazPrincipal.panelMenuJugador.activacionBotones(true);
//            });
            interfazPrincipal.actualizacionGeneralPanelMenuJugador();
            while (supervivienteActual.getAcciones() > 0) {
                // Mostrar el panel de control del jugador
                //interfazPrincipal.cardLayout.show(interfazPrincipal.panelDerechoPrincipal, "PanelMenuJugador");
                //interfazPrincipal.panelMenuJugador.activacionBotones(true);
                interfazPrincipal.actualizacionGeneralPanelMenuJugador();
                synchronized (monitor) {
                    // Esperar a que el jugador seleccione una acción
                    while (!interfazPrincipal.accionRealizada) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                        }
                    }
                    interfazPrincipal.accionRealizada = false;
                }
//                SwingUtilities.invokeLater(() -> {
//                    // Actualizar para el siguiente jugador
//                    interfazPrincipal.panelMenuJugador.actualizarLabels();
//                    interfazPrincipal.panelMenuJugador.activacionBotones(true);
//                });
                interfazPrincipal.actualizacionGeneralPanelMenuJugador();
            }
            //faseZombie();
            //faseApariciónZombi();
            avanzarTurno();
        } 
    }


    public void accionRealizada(){
        synchronized (monitor) {
            interfazPrincipal.actualizacionGeneralPanelMenuJugador();
            interfazPrincipal.accionRealizada = true;
            monitor.notifyAll();
        }
    }

    public void iniciarPartida(){
        tablero = new Tablero(this);
        almacen =  new AlmacenDeAtaques();

        // LLamamos a la InterfazPrincipal
        interfazPrincipal = new InterfazPrincipal(this);
    }

    public Partida(){
        Thread hiloPrincipal = new Thread(this::iniciarPartida);
        hiloPrincipal.start();
    }
}