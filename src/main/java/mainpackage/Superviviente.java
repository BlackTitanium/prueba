package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;

public class Superviviente extends Entidad implements Serializable{
    private String nombre;
    private int contadorZombis, mordeduras, acciones = 3;
    private accion seleccion; //Mover o atacar
    private Arma[] armas = new Arma[2];
    private Arma armaActiva;
    public enum estado {VIVO, MUERTO};
    private estado estadoActual;
    private Equipo[] inventario = new Equipo[5];
    private Ataque ultimoAtaque;
    private Partida partida;
    private ArrayList<String> zombisAsesinados;

    public int getContadorZombis() {
        return contadorZombis;
    }

    public int getMordeduras() {
        return mordeduras;
    }

    public int getAcciones() {
        return acciones;
    }

    public accion getSeleccion() {
        return seleccion;
    }
    public void setSeleccion(accion seleccion) {
        this.seleccion = seleccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Arma[] getArmas() {
        return armas;
    }

    public Arma getArmas(int a){
        return armas[a];
    }

    public void setArmaActiva(int ranura) {
        this.armaActiva = armas[ranura];
    }

    public Arma getArmaActiva() {
        return armaActiva;
    }
    
    public void setArma(Arma a, int b){
        this.armas[b] = a;
    }

    public estado getEstadoActual() {
        return estadoActual;
    }
    public Equipo[] getInventario() {
        return inventario;
    }

    public Equipo getInventario(int a){
        return inventario[a];
    }
    public void setInventario(Equipo e, int a){
        System.out.println("Se añade al inventario de " + this.nombre + ": ranura: " + a + ", y es: equipo: " + e.toString());
        inventario[a] = e;
    }

    public int getNumeroProvisiones() {
        int contador = 0;
        for (Equipo item : inventario) {
            if (item instanceof Provision) {
                contador++;
            }
        }
        return contador;
    }
    
    public int getNumeroArmas() {
        int contador = 0;
        for (Equipo item : inventario) {
            if (item instanceof Arma) {
                contador++;
            }
        }
        if(armas[0] != null){
            contador++;
        }
        if(armas[1] != null){
            contador++;
        }
        return contador;
    }

    public int getNumeroArmasActivas(){
        int contador = 0;
        for (Arma item : armas) {
            if (item != null) {
                contador++;
            }
        }
        return contador;
    }

    public void setEstado(estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Superviviente(String nombre, Casilla c, Partida p){
        super(p, c);
        this.partida = p;
        this.nombre = nombre;
        this.contadorZombis = 0;
        this.mordeduras = 0;
        this.estadoActual = estado.VIVO;
        this.zombisAsesinados = new ArrayList<>();
    }

    public void addMordeduras(){
        mordeduras++;
    }

    public void lessMordeduras(){
        if(mordeduras !=0 ){
            mordeduras--;
            partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
        }
    }

    public void addAcciones(){
        acciones++;
    }

    public void lessAcciones(){
        acciones--;
    }

    public void lessAcciones(int n){
        acciones = acciones - n;
    }

    public Ataque getUltimoAtaque() {
        return ultimoAtaque;
    }

    public void setUltimoAtaque(Ataque a){
        this.ultimoAtaque = a;
    }

    public void setAcciones(int a){
        this.acciones = a;
    }

    public void buscar(int a, Equipo equipo){
        setInventario(equipo, a);
        System.out.println("Se añade EN BUSCAR al inventario de " + this.nombre + ": ranura: " + a + ", y es: equipo: " + equipo.toString());
        partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
        acciones--;
    }

    public void activar(int ranura, int x, int y, Equipo e) {
        if (estadoActual == estado.MUERTO) {
            acciones = 0;
        }  else {
            if(seleccion==accion.MOVER){
                mover(x,y);
            } else if(seleccion==accion.ATACAR){
                atacar(ranura);
            } else if(seleccion==accion.BUSCAR){
                buscar(ranura, e);
            } else if(seleccion==accion.NADA){
                this.setAcciones(0);
            }
        }
    }

    public void intercambiarObjetos(int ranura1, int ranura2, int tipo){
        if(tipo == 1){ // Mover objeto de inventario a inventario
            Equipo aux = inventario[ranura1];
            inventario[ranura1] = inventario[ranura2];
            inventario[ranura2] = aux;
            acciones--;
            partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
        } else if(tipo == 2){ // Mover objeto de inventario a arma
            if(inventario[ranura1] instanceof Arma){
                Equipo aux = inventario[ranura1];
                inventario[ranura1] = armas[ranura2];
                armas[ranura2] = (Arma) aux;
                acciones--;
                partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
            } else {
                partida.getInterfazPrincipal().mostrarMensaje("En las armas activas solo puede haber armas");
            }
        } else if(tipo == 3){ // Mover objeto de arma a inventario
            if(getNumeroArmasActivas() > 1){
                if(inventario[ranura2] instanceof Provision){
                    partida.getInterfazPrincipal().mostrarMensaje("En las armas activas solo puede haber armas");
                } else {
                    Equipo aux = armas[ranura1];
                    armas[ranura1] = (Arma) inventario[ranura2];
                    inventario[ranura2] = aux;
                    acciones--;
                    partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
                }                
            } else {
                partida.getInterfazPrincipal().mostrarMensaje("Debes tener al menos 1 arma activa");
            }
        } else if(tipo == 4){ // Mover objeto de arma a arma
            Equipo aux = armas[ranura1];
            armas[ranura1] = armas[ranura2];
            armas[ranura2] = (Arma) aux;
            acciones--;
            partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
        }
    }

    public void usarObjeto(int ranura){
        if(inventario[ranura] instanceof Provision){
            Provision provision = elegirProvision(ranura);
            System.out.println("Voy a usar la provision: " + provision.getNombre() + " numero de mordeduras: " + mordeduras);
            lessMordeduras();
            System.out.println("Ya he usado la provision: " + provision.getNombre() + " numero de mordeduras despues de usarla: " + mordeduras);
            inventario[ranura] = null; // Quitamos la provision
            partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
            acciones--;
            partida.getInterfazPrincipal().panelMenuJugador.actualizarLabels();
        } else {
            partida.getInterfazPrincipal().mostrarMensaje("Debes seleccionar una provision");
        }
    }

    public Provision elegirProvision(int a){
        Equipo equipoSeleccionado = inventario[a];
        Provision provision = (Provision) equipoSeleccionado;
        return provision;
    }

    public void mover(int x, int y) {
        super.mover(x,y);
        for (int i=0; i<=casillaActual.getContadorZombis(); i++){
            acciones--;
        }
    }
    
    public void atacar(int a) {
        ultimoAtaque = new Ataque(armas[a], partida.getAlmacenDeAtaques());
        acciones--;
        partida.mostrarUltimoAtaque();
    }
    
    public String infoSuperviviente(){
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Estado: ").append(estadoActual).append("\n");
        sb.append("Acciones por turno: ").append(acciones).append("\n");
        sb.append("Inventario: ");
        for(int i = 0; i < inventario.length; i++){
            if(inventario[i]!= null){
                sb.append(inventario[i].getNombre()).append(", ");
            }
        }
        sb.append("\nArmas activas: ");
        if(armas[1] == null){
            sb.append(armas[0].getNombre()).append("\n");
        }else{
            sb.append(armas[0].getNombre()).append(" y ").append(armas[1].getNombre()).append("\n");
        }        
        sb.append("Contador de zombis: ").append(contadorZombis).append("\n");
        sb.append("Tipo de ataque recibido: ");
        return sb.toString();
    }

    public void añadirZombiAsesinado(String infoZombiAsesinado){
        zombisAsesinados.add(infoZombiAsesinado);
    }

    public String mostrarHistorialZombisAsesinados(){
        if(zombisAsesinados.isEmpty()){
            return nombre + " no ha asesinado a ningún zombi todavía\n";
        }else{
            StringBuilder sb = new StringBuilder();
            for(String zombi : zombisAsesinados){
                sb.append(zombi);
            }
            return sb.toString();
        }
    }
        
}