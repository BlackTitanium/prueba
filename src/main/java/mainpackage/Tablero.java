package mainpackage;

import java.io.Serializable;
import java.util.ArrayList;
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
        posicionesOcupadas[0][0] = true; // Inicio Jugadores
        posicionesOcupadas[9][9] = true; // Meta
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

    public Casilla calcularMovimientoZombi(Casilla casillaActualZombi) {
        Casilla objetivo = objetivoZombi(casillaActualZombi);
        if (objetivo == null) {
            return casillaActualZombi; // No hay supervivientes en el mapa
        }
    
        Casilla mejorCasilla = null;
        double distanciaMinima = Double.MAX_VALUE;
    
        // Posibles movimientos: arriba, abajo, izquierda, derecha, y diagonales
        int[] deltaX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] deltaY = {-1, 0, 1, -1, 1, -1, 0, 1};
    
        for (int i = 0; i < deltaX.length; i++) {
            int nuevaX = casillaActualZombi.getX() + deltaX[i];
            int nuevaY = casillaActualZombi.getY() + deltaY[i];
    
            // Verificar que la nueva posición esté dentro del mapa
            if (nuevaX >= 0 && nuevaX < 10 && nuevaY >= 0 && nuevaY < 10) {
                Casilla casillaAdyacente = mapa[nuevaX][nuevaY];
                double distancia = Math.sqrt(Math.pow((casillaAdyacente.getX() - objetivo.getX()), 2) + Math.pow((casillaAdyacente.getY() - objetivo.getY()), 2));
    
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    mejorCasilla = casillaAdyacente;
                }
            }
        }
    
        return mejorCasilla != null ? mejorCasilla : casillaActualZombi;
    }    

    public ArrayList<Casilla> elegirObjetivoSuperviviente(Arma arma, int xCentro, int yCentro){
        Casilla temp = null;
        int alcance = arma.getAlcance();
        ArrayList<Casilla> casillasEnRango = new ArrayList<>();
        if (alcance == 0){
            casillasEnRango.add(mapa[xCentro][yCentro]);
            return casillasEnRango;
        }else{
            for(int i = -alcance; i <= alcance; i++){
                for(int j = -alcance; j <= alcance; j++){
                    if(i != 0 || j != 0){
                        int x = xCentro + i;
                        int y = yCentro + j;
                        // Verificar que los índices estén dentro de los límites válidos
                        if (x >= 0 && x < mapa.length && y >= 0 && y < mapa[0].length){
                            temp = mapa[x][y];
                            if (temp != null){
                                casillasEnRango.add(temp);
                            }
                        }
                    }
                }
            }
            return casillasEnRango;
        }
        
    }
}