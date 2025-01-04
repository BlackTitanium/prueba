package mainpackage;

import java.io.Serializable;

public abstract class Entidad implements Serializable{
    protected int[] posicion = new int[2];
    protected Casilla casillaActual;
    public enum accion {MOVER, ATACAR, BUSCAR};
    protected Tablero tableroActual;

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
        posicion[0] += x;
        posicion[1] += y;
        Casilla casillaAnterior = casillaActual;
        casillaActual = tableroActual.getCasilla(posicion[0], posicion[1]);
        casillaAnterior.removeEntidad(this);
        casillaActual.addEntidad(this);
    }

    /*  atacar() y activar() no van a ser implementados en esta clase, ya que los Zombis
        no reciben un argumento en los metodos y los Supervivientes si.
    */
}