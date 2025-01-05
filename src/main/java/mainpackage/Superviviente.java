package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Superviviente extends Entidad implements Serializable{
    private String nombre;
    private int contadorZombis, mordeduras, acciones = 3;
    private accion seleccion; //Mover o atacar
    private Arma[] armas = new Arma[2];
    public enum estado {VIVO, MUERTO};
    private estado estadoActual;
    private Equipo[] inventario = new Equipo[5]; 
    private Ataque ultimoAtaque;


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

    public Arma[] getArmas() {
        return armas;
    }
   

    public estado getEstadoActual() {
        return estadoActual;
    }
    public Equipo[] getInventario() {
        return inventario;
    }
    public void setInventario(Equipo e, int a){
        this.inventario[a] = e;
    }

    public void setEstado(estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Superviviente(String nombre, Casilla c, Tablero t){
        super(t, c);
        //tableroActual = t;
        //casillaActual = c;
        this.nombre = nombre;
        this.contadorZombis = 0;
        this.mordeduras = 0;
        this.estadoActual = estado.VIVO;
        this.casillaActual = c;
    }

    public void addMordeduras(){
        mordeduras++;
        if (mordeduras == 2){
            estadoActual = estado.MUERTO;
            casillaActual.removeSuperviviente(this);
        }
    }

    public void lessMordeduras(){
        if(mordeduras !=0 ){
            mordeduras--;
        }
    }

    public void addAcciones(){
        acciones++;
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

    public void buscar(int a){
        inventario[a] = casillaActual.buscar();
        acciones--;
    }

    public void activar(int ranura, int x, int y) {
        System.out.println("En Activar: Supervivientes: " + tableroActual.getCasilla(x, y).getContadorSupervivientes() + " Zombis: " + tableroActual.getCasilla(x, y).getContadorZombis());
        if (estadoActual == estado.MUERTO) {
            acciones = 0;
        }  else {
            if(seleccion==accion.MOVER){
                mover(x,y);

            } else if(seleccion==accion.ATACAR){
                atacar(ranura);

            } else {
                buscar(ranura);
            }
        }
    }

    
    public void mover(int x, int y) {
        System.out.println("En Mover de Superviviente: Supervivientes: " + tableroActual.getCasilla(x, y).getContadorSupervivientes() + " Zombis: " + tableroActual.getCasilla(x, y).getContadorZombis());
        //super.moverSuperviviente(x, y, this);
        super.mover(x,y);
        for (int i=0; i<=casillaActual.getContadorZombis(); i++){
            acciones--;
        }
    }

    public void elegirArma(int a, int b){
        if(inventario[a] instanceof Arma){
            Equipo equipoSeleccionado = inventario[a];
            armas[b] = (Arma) equipoSeleccionado;
        } else {
            inventario[a] = null;
            lessMordeduras();
        }
    }

    public Provision elegirProvision(int a){
        if(inventario[a] instanceof Provision){
            Equipo equipoSeleccionado = inventario[a];
            Provision provision = (Provision) equipoSeleccionado;
            acciones--;
            return provision;
        } else {
            throw new IllegalArgumentException("No es una provision");
        }
    }

    public ArrayList<Casilla> elegirObjetivo(Arma arma){
        Casilla temp = null;
        int alcance = arma.getAlcance();
        if (alcance == 0){
            return null;
        }
        ArrayList<Casilla> casillasEnRango = new ArrayList<>();
        for(int i = -alcance; i <= alcance; i++){
            for(int j = -alcance; j <= alcance; j++){
                if(i != 0 || j != 0){
                    temp = tableroActual.getCasilla(posicion[0] + i, posicion[1] + j);
                    if(temp != null){
                        casillasEnRango.add(temp);
                    }
                }
            }
        }
        return casillasEnRango;
    }
    
    public void atacar(int a) {
        acciones--;
        ultimoAtaque = new Ataque(armas[a]);
    }
    
}