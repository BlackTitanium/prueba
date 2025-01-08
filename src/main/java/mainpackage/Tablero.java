package mainpackage;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable{
    private Casilla mapa[][];
    private Random random = new Random();
    public boolean[][] posicionesOcupadas = new boolean[10][10];
    public boolean[][] posicionesBuscadas = new boolean[10][10];

    private Partida partida;

    public void setMapa(Casilla c){
        mapa[c.getX()][c.getY()] = c;
    }

    public Tablero(Partida partida){
        this.partida = partida;
        mapa = new Casilla[10][10];
        for (int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                mapa[i][j] = new Casilla(i, j);
                posicionesBuscadas[i][j] = false;
            }
        }
    }
    
//    public void mostrar(){
//        for(int i = 0; i < 10; i++){
//            for(int j = 0; j < 10; j++){
//                System.out.print("[" + mapa[i][j].getContenido() + "]");
//            }
//            System.out.println();
//        }
//    }

    public Casilla getCasilla(int a, int b){
        return mapa[a][b];
    }

    public Casilla getCasilla(Casilla c){
        return mapa[c.getX()][c.getY()];
    }
    public void reiniciarTablero(){
        for (int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if(i!=0 && j!= 0){
                    continue;
                } else {
                mapa[i][j].reiniciarCasilla();
                }
            }
        }
    }

    // Devuelve la casilla del Superviviente más cercano al Zombi que llama al método
    public Casilla objetivoZombi(Casilla casillaActualZombi) {
        Casilla objetivo = null;
        double distanciaMinima = Double.MAX_VALUE;
        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 10; k++) {
                if (mapa[j][k].getContadorSupervivientes() != 0) {
                    double distancia = Math.sqrt(Math.pow((casillaActualZombi.getX() - mapa[j][k].getX()), 2) + Math.pow((casillaActualZombi.getY() - mapa[j][k].getY()), 2));
                    if (distancia < distanciaMinima) {
                        distanciaMinima = distancia;
                        objetivo = mapa[j][k];
                    }
                }
            }
        }
        return objetivo;
    }
}