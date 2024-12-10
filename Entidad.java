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

    public abstract void activar();

    public void mover(int x, int y){
        posicion[0] += x;
        posicion[1] += y;
    }

    public abstract void atacar(Entidad e);

}