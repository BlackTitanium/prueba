package mainpackage;

import java.io.Serializable;
import java.util.Random;

public class Tablero implements Serializable{
    private Casilla mapa[][];
    private Random random = new Random();
    public void setMapa(Casilla c){
        mapa[c.getX()][c.getY()] = c;
    }

    public Tablero(){
        for (int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                mapa[i][j] = new Casilla(i, j);
            }
        }
    }
    
    public void mostrar(){
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                System.out.print("[" + mapa[i][j].getContenido() + "]");
            }
            System.out.println();
        }
    }

    public Casilla getCasilla(int a, int b){
        return mapa[a][b];
    }

    public void reiniciarTablero(){
        for (int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                mapa[i][j].reiniciarCasilla();
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

    public void aparicionZombi(){
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int tipo = random.nextInt(3);
        int subtipo = random.nextInt(3);
        Zombi nuevoZombi =  null;
        switch(subtipo){
            case 0:
                nuevoZombi = new Zombi(this, mapa[x][y], Zombi.tiposZombi[tipo], "NORMAL");
                break;
            case 1:
                nuevoZombi = new Toxico(this, mapa[x][y], Zombi.tiposZombi[tipo], "TOXICO");
                break;
            case 2:
                nuevoZombi = new Berserker(this, mapa[x][y], Zombi.tiposZombi[tipo], "BERSERKER");
                break;
        }
        mapa[x][y].addZombi(nuevoZombi);
    }
}