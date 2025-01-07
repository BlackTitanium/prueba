package mainpackage;

import java.io.Serializable;

public abstract class Entidad implements Serializable{
    protected int[] posicion = new int[2];
    protected Casilla casillaActual;
    public enum accion {MOVER, ATACAR, BUSCAR, INVENTARIO, NADA};
    protected Tablero tableroActual;
    private Partida partida;

    public Entidad(Partida p, Casilla c){
        partida = p;
        tableroActual = partida.getTablero();
        casillaActual = tableroActual.getCasilla(c.getX(), c.getY());
        setPosicion(c.getX(), c.getY());
    }

    public void setCasillaActual(Casilla c){
        casillaActual = c;
    }

    public Casilla getCasillaActual(){
        return casillaActual;
    }

    int[] getPosicion(){
        return posicion;
    }
    void setPosicion(int x, int y){
        posicion[0] = x;
        posicion[1] = y;
    }

    public void mover(int x, int y){
        System.out.println("Principio de Mover en Entidad: Supervivientes(CO): " + tableroActual.getCasilla(posicion[0], posicion[1]).getContadorSupervivientes() + " Zombis: " + tableroActual.getCasilla(posicion[0], posicion[1]).getContadorZombis());
        System.out.println("Origen: x: " + posicion[0] + ", y: " + posicion[1]);
        System.out.println("Principio de Mover en Entidad: Supervivientes(CD): " + tableroActual.getCasilla(x, y).getContadorSupervivientes() + " Zombis: " + tableroActual.getCasilla(x, y).getContadorZombis());
        System.out.println("Destino: x: " + x + ", y: " + y);
        tableroActual.moverSuperviviente(posicion[0], posicion[1], x, y);
        posicion[0] = x;
        posicion[1] = y;
        Casilla casillaAnterior = casillaActual;
        casillaActual = tableroActual.getCasilla(posicion[0], posicion[1]);
        //casillaAnterior.removeEntidad(this);
        //casillaActual.addEntidad(this);
        System.out.println("Final de Mover en Entidad: Supervivientes: " + tableroActual.getCasilla(x, y).getContadorSupervivientes() + " Zombis: " + tableroActual.getCasilla(x, y).getContadorZombis());
        System.out.println("Destino: x: " + x + ", y: " + y);
    }

    /*  atacar() y activar() no van a ser implementados en esta clase, ya que los Zombis
        no reciben un argumento en los metodos y los Supervivientes si.
    */
}