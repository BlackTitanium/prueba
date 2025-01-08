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
        casillaActual = c;
        setPosicion(c.getX(), c.getY());
    }

    public void setCasillaActual(Casilla c){
        casillaActual = c;
        setPosicion(c.getX(), c.getY());
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
        posicion[0] = x;
        posicion[1] = y;
        Casilla casillaAnterior = casillaActual;
        casillaActual = tableroActual.getCasilla(x, y);
        casillaAnterior.removeEntidad(this);
        casillaActual.addEntidad(this);
    }
}