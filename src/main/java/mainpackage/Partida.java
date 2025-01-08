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
    public boolean derrota = false;
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
            Arma arma = new Arma();
            Provision provision = new Provision();
            Superviviente s = new Superviviente(nombres[i], casillaInicial, tablero, this);
            s.setArma(arma, 0);
            s.setArmaActiva(0);
            s.setInventario(provision, 0);
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
                    ArrayList<Casilla> objetivo = supervivienteActual.elegirObjetivo(supervivienteActual.getArmas()[ranura]);
                    // Hacemos las casillas
                    Casilla casillaActual = supervivienteActual.getCasillaActual();
                    Casilla casillaObjetivo = tablero.getCasilla(x, y);
                    // Activamos el superviviente y recogemos el ataque y su numero de exitos
                    supervivienteActual.activar(ranura,casillaActual.getX(),casillaActual.getY());
                    Ataque ataque = supervivienteActual.getUltimoAtaque();
                    int nExitos = ataque.getNumExitos(); // Cogemos el numero de exitos
                    // Hacemos el arma seleccionada
                    Arma armaSeleccionada = supervivienteActual.getArmas()[ranura];
                    // Intento a 0
                    int intento = 0;
                    // Mientras haya zombis en la casilla y haya exitos y el superviviente este vivo
                    while((intento < casillaObjetivo.getContadorZombis() || nExitos > 0)){
                        try {
                            // Reaccion del zombi
                            casillaObjetivo.getZombi(intento).reaccion(armaSeleccionada);
                            if(casillaObjetivo.getZombi(intento).getEstadoActual() == Zombi.estado.MUERTO){ // EL zombi a muerto
                                // Añadir el zombi a la lista de zombis asesinados
                                supervivienteActual.añadirZombiAsesinado(casillaObjetivo.getZombi(intento).infoZombi());
                                // Quitar el zombi de la casilla
                                casillaObjetivo.removeEntidad(casillaObjetivo.getZombi(intento));
                                // Quitar el zombi de la lista
                                zombis.remove(casillaObjetivo.getZombi(intento));
                                // Borramos el zombi en la interfaz
                                interfazPrincipal.actualizarCasillas(casillaObjetivo, casillaObjetivo);
                                // Como ha matado a un zombi se resta un exito
                                nExitos--;
                            }
                            if(supervivienteActual.getEstadoActual() == Superviviente.estado.MUERTO){ // El superviviente a muerto
                                supervivienteMuerto();
                            }
                        } catch (IllegalArgumentException e) {
                            if ("Alcance".equals(e.getMessage())) { // Si es Berserker
                                supervivienteActual.addAcciones(); // Recupera la accion perdida en activar
                            }
                        }
                        intento++; // Siguiente zombi
                    }
                    break;
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
        accionTerminada();
    }

    public void supervivienteMuerto(){
        // Cambiar el nombre del superviviente a nombre☠
        String nombre = supervivienteActual.getNombre();
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("<span style='color:red;'>"); // Color rojo
        sb.append(nombre).append("☠"); // Nombre del superviviente + ☠
        sb.append("</span>"); // Cerrar el color
        sb.append("</html>");
        supervivienteActual.setNombre(sb.toString());
        // Quitar las acciones del superviviente
        supervivienteActual.setAcciones(0);
        // Quitar el superviviente de la interfaz
        interfazPrincipal.actualizarCasillas(supervivienteActual.getCasillaActual(), supervivienteActual.getCasillaActual());
        // Quitar el superviviente de la lista
        supervivientes.remove(supervivienteActual);
        // Si no quedan supervivientes
        if(supervivientes.isEmpty()){
            derrota = true;
            // Mostrar mensaje de derrota
            interfazPrincipal.mostrarMensajeDeDerrota();
        }
    }

    public void faseSuperviviente(){ //eleccion viene del input de la interfaz
        if(supervivienteActual.getEstadoActual() != Superviviente.estado.MUERTO){
            supervivienteActual = this.getSupervivienteActual();
            inventarioActual = supervivienteActual.getInventario();
            supervivienteActual.setAcciones(3);
            System.out.println("Acciones restantes: " + supervivienteActual.getAcciones() + " de " + supervivienteActual.getNombre());
        }        
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
        while(!victoria && !derrota){
            faseSuperviviente();
            Superviviente supervivienteActual = getSupervivienteActual();
            interfazPrincipal.actualizacionGeneralPanelMenuJugador();
            while (supervivienteActual.getAcciones() > 0) {
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
            }
            //faseZombie();
            //faseApariciónZombi();
            avanzarTurno();
        } 
    }


    public void accionTerminada(){
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