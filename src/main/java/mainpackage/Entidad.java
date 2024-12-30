package mainpackage;

public abstract class Entidad {
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

    public abstract void activar(int a);

    public void mover(int x, int y){
        posicion[0] += x;
        posicion[1] += y;
        casillaActual = tableroActual.getCasilla(posicion[0], posicion[1]);
    }

    /*  atacar() no va a ser implementado en esta clase, ya que los Zombis
        no reciben un argumento en el metodo y los Supervivientes si.
    */
}