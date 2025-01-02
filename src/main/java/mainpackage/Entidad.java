package mainpackage;

import java.io.Serializable;

public abstract class Entidad implements Serializable{
    protected int[] posicion = new int[2];
    protected Casilla casillaActual;
    public enum accion {MOVER, ATACAR};
    protected Tablero tableroActual;

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
        casillaActual = tableroActual.getCasilla(posicion[0], posicion[1]);
    }

    /*  atacar() y activar() no van a ser implementados en esta clase, ya que los Zombis
        no reciben un argumento en los metodos y los Supervivientes si.
    */
}