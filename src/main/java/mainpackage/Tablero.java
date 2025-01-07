package mainpackage;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable{
    private Casilla mapa[][];
    private Random random = new Random();
    public boolean[][] posicionesOcupadas = new boolean[10][10];

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

    public Casilla objetivoZombi(Casilla c){
        Casilla objetivo = null;
        for (int j=0; j<10; j++){
                    for(int k=0; k<10; k++){
                        if(mapa[j][k].getContadorSupervivientes()!=0){
                            if(Math.sqrt(Math.pow((c.getX()-mapa[j][k].getX()),2)-Math.pow((c.getY()-mapa[j][k].getY()),2))<
                                Math.sqrt(Math.pow((c.getX()-objetivo.getX()),2)-Math.pow((c.getY()-objetivo.getY()),2))){
                                    objetivo = mapa[j][k];
                                }
                        }
                    }
                }
        return objetivo;
    }
}